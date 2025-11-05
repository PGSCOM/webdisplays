# Migración a NeoForge 21.1 - Estado y Trabajo Pendiente

## ✅ Cambios Completados

### 1. Configuración Base
- ✅ Actualizado `neo_version` de 21.1.213 a 21.1.192 en gradle.properties
- ✅ Actualizada configuración de memoria (6GB para decompilador)
- ✅ Ejecutado `clean --refresh-dependencies`

### 2. Migración de Paquetes (Imports)
- ✅ Migrados 48 archivos de `net.minecraftforge.*` a `net.neoforged.*`
- ✅ Corregidos problemas de UTF-8 BOM en archivos migrados
- ✅ Cambiado `MinecraftForge.EVENT_BUS` a `NeoForge.EVENT_BUS`
- ✅ Cambiado `net.minecraftforge.api.distmarker.Dist.CLIENT` a `net.neoforged.api.distmarker.Dist.CLIENT`

### 3. Sistema de Redes (Parcial)
- ✅ Creada clase base `WDPayload` extendiendo `CustomPacketPayload`
- ✅ Creada clase helper `PayloadEntry` para registro de payloads
- ✅ Actualizados imports en mensajes client_bound y server_bound
- ✅ Convertidos manualmente 3 mensajes: S2CMessageServerInfo, S2CMessageCloseGui, S2CMessageMiniservKey

## ⚠️ TRABAJO CRÍTICO PENDIENTE

### 1. Sistema de Redes - INCOMPLETO ⚠️
El sistema de redes de NeoForge 21.x es completamente diferente a Forge. Lo que falta:

#### a) Conversión de Mensajes Restantes
Archivos que necesitan conversión manual de `Packet` a `WDPayload`:

**Client Bound (5 restantes):**
- S2CMessageOpenGui.java
- S2CMessageAddScreen.java  
- S2CMessageScreenUpdate.java
- S2CMessageACResult.java
- S2CMessageJSResponse.java

**Server Bound (5 archivos):**
- C2SMessageScreenCtrl.java
- C2SMessageRedstoneCtrl.java
- C2SMessageMiniservConnect.java
- C2SMessageMinepadUrl.java
- C2SMessageACQuery.java

**Patrón de Conversión:**
```java
// ANTES (Forge)
public class MyMessage extends Packet {
    public void handle(NetworkEvent.Context ctx) {
        if (checkClient(ctx)) {
            ctx.enqueueWork(() -> { /* ... */ });
            ctx.setPacketHandled(true);
        }
    }
}

// DESPUÉS (NeoForge)
public class MyMessage extends WDPayload {
    public static final CustomPacketPayload.Type<MyMessage> TYPE = 
        new CustomPacketPayload.Type<>(new ResourceLocation("webdisplays", "my_message"));
    
    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
    
    @Override
    public void handle(IPayloadContext context) {
        if (isClient(context)) {
            context.enqueueWork(() -> { /* ... */ });
            // NO se necesita setPacketHandled
        }
    }
}
```

#### b) Reescribir WDNetworkRegistry.java
El archivo actual usa `SimpleChannel` que ya no existe. Debe migrar a:
```java
@EventBusSubscriber(modid = "webdisplays", bus = EventBusSubscriber.Bus.MOD)
public class WDNetworkRegistry {
    
    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("webdisplays")
            .versioned("1.0")
            .optional();
        
        // Registrar cada payload
        registrar.playBidirectional(
            S2CMessageServerInfo.TYPE,
            StreamCodec.of(/* ... */),
            (payload, context) -> payload.handle(context)
        );
        
        // ... registrar todos los demás payloads
    }
}
```

#### c) Actualizar ScreenControl.java y sus Implementaciones
Todas las clases en `controls/builtin/` usan `NetworkEvent.Context`, deben cambiar a `IPayloadContext`:
- AutoVolumeControl.java
- ClickControl.java
- JSRequestControl.java
- KeyTypedControl.java
- LaserControl.java
- ManageRightsAndUpdgradesControl.java
- ModifyFriendListControl.java
- OwnerControl.java
- ScreenModifyControl.java
- SetURLControl.java
- TurnOffControl.java

### 2. Sistema de Eventos - BLOQUEANTE ⚠️
NeoForge 21.x cambió completamente el sistema de eventos de tick:

**Archivos Afectados:**
- `ClientProxy.java` - usa `TickEvent.ClientTickEvent` y `TickEvent.LevelTickEvent`
- `Packet.java` - usa `TickEvent.RenderTickEvent`
- `KeyboardCamera.java` - usa `TickEvent.ClientTickEvent`

**Cambios Necesarios:**
```java
// ANTES
@SubscribeEvent
public void onTick(TickEvent.ClientTickEvent event) {
    if (event.phase == TickEvent.Phase.END) {
        // código
    }
}

// DESPUÉS - NeoForge 21.x
// Los eventos de tick ahora son diferentes, necesitas usar:
// - ClientTickEvent.Post para cliente
// - LevelTickEvent.Post para mundo
// - RenderFrameEvent para render
```

### 3. Sistema de Configuración
**Archivos Afectados:**
- `AnnoCFG.java` - usa `ForgeConfigSpec` y `ModConfigEvent`
- `ClientConfig.java` - usa `FMLJavaModLoadingContext`
- `CommonConfig.java` - usa `FMLJavaModLoadingContext`

**Cambios:**
- `ForgeConfigSpec` → Sigue siendo válido pero el paquete cambió
- `ModConfigEvent` → Cambió el paquete
- `FMLJavaModLoadingContext` → Cambió a `ModLoadingContext` o similar

### 4. Sistema de Capabilities - BLOQUEANTE ⚠️
**Archivo Principal:** `WDDCapability.java`

NeoForge 21.x reescribió completamente el sistema de capabilities. Los cambios incluyen:
- `Capability<T>` ya no existe como antes
- `LazyOptional` fue eliminado
- `ICapabilitySerializable` cambió
- `CapabilityManager` es diferente

Este archivo necesita una reescritura completa siguiendo la nueva API de capabilities de NeoForge.

### 5. Sistema de Advancements/Criterios
**Archivo Afectado:** `Criterion.java`

Usa clases que cambiaron:
- `AbstractCriterionTriggerInstance` 
- `DeserializationContext`

Estas clases fueron movidas o renombradas en 1.21.x

### 6. PacketDistributor Changes
**Archivos Afectados:**
- `PeripheralBlock.java` - usa `PacketDistributor.TargetPoint`
- `ScreenConfigData.java` - usa `PacketDistributor.TargetPoint`

`PacketDistributor.TargetPoint` fue eliminado. Ahora se usa:
```java
// ANTES
PacketDistributor.TargetPoint point = new PacketDistributor.TargetPoint(x, y, z, range, level);

// DESPUÉS  
PacketDistributor.sendToPlayersNear(level, null, x, y, z, range, payload);
```

### 7. Client Rendering APIs
**Archivo Afectado:** `ScreenModelLoader.java`

Usa `ForgeHooksClient` que ya no existe o cambió de ubicación.

## 📋 Plan de Acción Recomendado

### Fase 1: Hacer que Compile (Mínimo Viable)
1. Comentar temporalmente todo el código de TickEvent
2. Comentar temporalmente WDDCapability
3. Completar conversión de todos los mensajes a WDPayload
4. Reescribir WDNetworkRegistry con RegisterPayloadHandlersEvent
5. Actualizar ScreenControl interfaces

### Fase 2: Restaurar Funcionalidad
1. Migrar eventos de Tick al nuevo sistema
2. Reescribir sistema de Capabilities
3. Actualizar sistema de configuración
4. Arreglar PacketDistributor
5. Corregir APIs de rendering

### Fase 3: Testing
1. Compilación exitosa
2. El mod carga sin crashes
3. Funcionalidades básicas funcionan
4. Networking funciona correctamente

## 🔗 Referencias Útiles

- [NeoForge Networking Refactor](https://neoforged.net/news/20.4networking-rework/)
- [NeoForge 21.2 Release Notes](https://neoforged.net/news/21.2release/)
- [Porting Primer 1.21.2](https://github.com/ChampionAsh5357/neoforged-github/blob/port/1212-or-122/primers/1.21.2/index.md)
- [NeoForge Documentation](https://docs.neoforged.net/)

## ⏱️ Estimación de Tiempo

- **Fase 1 (Compilación):** 4-6 horas de trabajo
- **Fase 2 (Funcionalidad):** 8-12 horas de trabajo
- **Fase 3 (Testing/Depuración):** 4-8 horas de trabajo

**Total Estimado:** 16-26 horas de desarrollo

## 🎯 Estado Actual

**Progreso:** ~30% completado
**Compilación:** ❌ No compila (100 errores)
**Funcional:** ❌ No funcional

**Siguiente Paso Crítico:** Completar conversión de mensajes de red y reescribir WDNetworkRegistry

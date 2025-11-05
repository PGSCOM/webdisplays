# WebDisplays - Migración a NeoForge 21.1.192 - TODO List

**Fecha**: 5 de noviembre de 2025  
**Estado**: 85% completado  
**Branch**: build  
**Versión objetivo**: Minecraft 1.21.1 + NeoForge 21.1.192

---

## ✅ COMPLETADO (85%)

### 1. Sistema de Networking (100%) ✨
- ✅ Creado `WDPayload.java` base class extends `CustomPacketPayload`
- ✅ Creado `PayloadEntry.java` helper para PayloadRegistrar
- ✅ Convertidos 13 mensajes de red:
  - `S2CMessageServerInfo`, `S2CMessageCloseGui`, `S2CMessageMiniservKey`
  - `S2CMessageOpenGui`, `S2CMessageACResult`, `S2CMessageJSResponse`
  - `S2CMessageAddScreen`, `S2CMessageScreenUpdate`
  - `C2SMessageScreenCtrl`, `C2SMessageRedstoneCtrl`, `C2SMessageMiniservConnect`
  - `C2SMessageMinepadUrl`, `C2SMessageACQuery`
- ✅ `WDNetworkRegistry.java` reescrito con `RegisterPayloadHandlersEvent`
- ✅ `ScreenControl.java` + 11 implementaciones actualizadas (NetworkEvent.Context → IPayloadContext)

### 2. Event System (100%) ✅
- ✅ `TickEvent` migrado:
  - `ClientTickEvent` → `net.neoforged.neoforge.client.event.ClientTickEvent`
  - `LevelTickEvent` sin `.Post` ni `.phase`
- ✅ Constructor de `WebDisplays` actualizado: `public WebDisplays(IEventBus modEventBus)`
- ✅ `FMLJavaModLoadingContext.get()` eliminado
- ✅ `ClientProxy.java` actualizado: `onTick(ClientTickEvent)`, `onLevelTick(LevelTickEvent)`
- ✅ `KeyboardCamera.java` actualizado: `gameTick(ClientTickEvent)`

### 3. Config System (100%) ✅
- ✅ `ForgeConfigSpec` → `ModConfigSpec` en 3 archivos:
  - `AnnoCFG.java`
  - `ClientConfig.java`
  - `CommonConfig.java`
- ✅ Método `init(IEventBus modEventBus)` implementado

### 4. Registry System (100%) ✅
- ✅ `ForgeRegistries` → `BuiltInRegistries`
- ✅ `RegistryObject` → `DeferredHolder`
- ✅ Archivos completados:
  - `BlockRegistry.java` (100%)
  - `TileRegistry.java` (100%)
  - `ItemRegistry.java` (100%)
  - `WDTabs.java` (100%)
  - `WebDisplays.SOUNDS` (100%)

### 5. Attributes (100%) ✅
- ✅ `ForgeMod.BLOCK_REACH` → `Attributes.BLOCK_INTERACTION_RANGE` (vanilla Minecraft)
- ✅ `C2SMessageRedstoneCtrl.java` actualizado

### 6. Annotations (100%) ✅
- ✅ `@Mod.EventBusSubscriber` → `@EventBusSubscriber`
- ✅ Import: `net.neoforged.fml.common.EventBusSubscriber`
- ✅ `ClientProxy.java` actualizado
- ✅ `GuiSetURL2.java` anotación eliminada (no necesaria)

### 7. Sintaxis (100%) ✅
- ✅ Bloques if-else corregidos en `ScreenBlockEntity.java`
- ✅ Llave duplicada eliminada en `BlockRegistry.java`
- ✅ Imports obsoletos eliminados

---

## ⏸️ ARCHIVOS DESHABILITADOS TEMPORALMENTE

### Archivos renombrados a `.disabled`:

| Archivo | Razón | Tiempo Estimado | Prioridad |
|---------|-------|-----------------|-----------|
| **WDDCapability.java** | Capabilities API completamente reescrita. Usar DataComponentType en lugar de Capability/LazyOptional/ICapabilitySerializable | **4-6 horas** | 🔴 ALTA |
| **Criterion.java** | Achievement API cambió. AbstractCriterionTriggerInstance y DeserializationContext eliminados | **2 horas** | 🟡 MEDIA |
| **ScreenModelLoader.java** | ForgeHooksClient.getBlockMaterial() no existe. Necesita alternativa para Material loading | **30 min** | 🟢 BAJA |
| **MouseHandlerMixin.java** | Obfuscation mapping incorrecto para @Inject target "onPress" | **1 hora** | 🟡 MEDIA |
| **OverlayMixin.java** | Obfuscation mapping incorrecto para @Inject target "renderCrosshair" y @Shadow fields | **1 hora** | 🟡 MEDIA |

### Código comentado en archivos activos:

#### WebDisplays.java
```java
// TODO: Capabilities system completely rewritten in NeoForge 21.x
/*
@SubscribeEvent
public static void onAttachPlayerCap(AttachCapabilitiesEvent<Entity> event) { ... }

@SubscribeEvent
public void attachEntityCaps(AttachCapabilitiesEvent<Entity> ev) { ... }

@SubscribeEvent
public void onPlayerClone(PlayerEvent.Clone ev) { ... }
*/

// TODO: Achievement API changed in 1.21.1
/*
public Criterion criterionPadBreak;
public Criterion criterionUpgradeScreen;
public Criterion criterionLinkPeripheral;
public Criterion criterionKeyboardCat;
private static void registerTrigger(Criterion ... criteria) { ... }
*/
```

#### ScreenConfigData.java
```java
// TODO: NeoForge 21.x - PacketDistributor.TargetPoint eliminated
/*
public void sendTo(PacketDistributor.TargetPoint tp) { ... }
*/
```

#### ClientProxy.java
```java
// TODO: ForgeHooksClient renamed/moved in NeoForge 21.x
/*
@SubscribeEvent
public static void onModelRegistryEvent(ModelEvent.RegisterGeometryLoaders event) {
    event.register(ScreenModelLoader.SCREEN_LOADER.getPath(), new ScreenModelLoader());
}
*/
```

#### webdisplays.mixins.json
```json
{
  "client": [
    // "MouseHandlerMixin",  // Disabled - obfuscation mapping issues
    // "OverlayMixin"        // Disabled - obfuscation mapping issues
  ]
}
```

---

## 🔧 ERRORES ACTIVOS (15 archivos - 100 errores)

### Categoría 1: Block/BlockEntity API Changes

#### PeripheralBlock.java (BLOQUEANTE)
**Errores**: ~15  
**Problema principal**: Falta método `codec()` requerido por `BaseEntityBlock` en 1.21.1
```java
// NECESARIO:
@Override
protected MapCodec<? extends PeripheralBlock> codec() {
    return CODEC;
}
```
**Complejidad**: Media (2 horas)  
**Impacto**: Afecta todos los bloques periféricos

#### AbstractPeripheralBlockEntity.java
**Errores**: ~5  
**Problema**: Override annotation incorrecta
**Complejidad**: Baja (30 min)

#### ScreenBlock.java
**Errores**: ~10  
**Problema**: Block API cambió, posibles cambios en métodos
**Complejidad**: Media (1-2 horas)

#### ScreenBlockEntity.java
**Errores**: ~15  
**Problema**: BlockEntity API cambió
**Complejidad**: Media (2 horas)

---

### Categoría 2: Rendering API Changes (BufferBuilder)

#### KeyboardBlockLeft.java
**Errores**: ~10  
**Problema principal**: `BufferBuilder.getBuilder()` eliminado en 1.21.1
```java
// ANTES (1.20.x):
BufferBuilder buffer = Tesselator.getInstance().getBuilder();

// AHORA (1.21.1):
// Tesselator ahora retorna BufferBuilder directamente
// Necesita investigación de nueva API
```
**Complejidad**: Alta (2-3 horas)  
**Archivos afectados**: KeyboardBlockLeft, KeyboardBlockRight, KeyboardBlockEntity

#### KeyboardBlockRight.java
**Errores**: ~10  
**Problema**: Mismo que KeyboardBlockLeft
**Complejidad**: Alta (2-3 horas)

#### KeyboardBlockEntity.java
**Errores**: ~8  
**Problema**: BufferBuilder API + rendering changes
**Complejidad**: Alta (2 horas)

---

### Categoría 3: Renderer API Changes

#### LaserPointerRenderer.java
**Errores**: ~5  
**Problema**: Renderer API cambió (PoseStack, VertexConsumer, etc.)
**Complejidad**: Media (1-2 horas)

#### MinePadRenderer.java
**Errores**: ~5  
**Problema**: Item renderer API cambió
**Complejidad**: Media (1 hora)

#### ModelMinePad.java
**Errores**: ~3  
**Problema**: Model API cambió
**Complejidad**: Baja (30 min)

---

### Categoría 4: Audio & Data

#### WDAudioSource.java
**Errores**: ~3  
**Problema**: Audio API cambió
**Complejidad**: Baja (30 min)

#### ScreenData.java
**Errores**: ~5  
**Problema**: Data structure changes
**Complejidad**: Media (1 hora)

#### GuiData.java
**Errores**: ~3  
**Problema**: GUI data structure
**Complejidad**: Baja (30 min)

---

### Categoría 5: Control Logic

#### ClickControl.java
**Errores**: ~2  
**Problema**: Control API changes
**Complejidad**: Baja (20 min)

#### ClientProxy.java (parcial)
**Errores**: ~5  
**Problema**: Algunos métodos de client API cambiaron
**Complejidad**: Baja (1 hora)

---

## 📋 PLAN DE ACCIÓN RECOMENDADO

### Fase 1: Correcciones Críticas (4-6 horas)
**Objetivo**: Lograr compilación básica sin errores

1. **Investigar BufferBuilder API** (2 horas) 🔴
   - Buscar documentación de cambios en 1.21.1
   - Actualizar KeyboardBlock* con nueva API
   
2. **Implementar codec() en PeripheralBlock** (1 hora) 🔴
   - Agregar MapCodec para serialización
   - Verificar herencia en subclases

3. **Corregir AbstractPeripheralBlockEntity** (30 min) 🟡
   - Remover @Override incorrectos
   
4. **Actualizar ScreenBlock** (1-2 horas) 🟡
   - Verificar métodos requeridos de Block API

### Fase 2: Rendering & Models (3-4 horas)
**Objetivo**: Restaurar funcionalidad de rendering

5. **Actualizar LaserPointerRenderer** (1 hora)
6. **Actualizar MinePadRenderer** (1 hora)
7. **Actualizar ModelMinePad** (30 min)
8. **Actualizar WDAudioSource** (30 min)

### Fase 3: Data & Controls (2-3 horas)
**Objetivo**: Finalizar lógica de control

9. **Actualizar ScreenData** (1 hora)
10. **Actualizar GuiData** (30 min)
11. **Actualizar ClickControl** (20 min)
12. **Finalizar ClientProxy** (1 hora)

### Fase 4: Características Avanzadas (8-12 horas)
**Objetivo**: Restaurar funcionalidad completa

13. **Reescribir WDDCapability** (4-6 horas) 🔴
    - Investigar DataComponentType system
    - Reemplazar Capability/LazyOptional
    - Implementar serialización

14. **Actualizar Criterion** (2 horas) 🟡
    - Nueva Achievement API
    - Trigger registration

15. **Actualizar ScreenModelLoader** (30 min) 🟢
    - Alternativa a ForgeHooksClient

16. **Corregir Mixins** (2 horas) 🟡
    - Actualizar obfuscation mappings
    - Verificar target methods

---

## 🔍 INVESTIGACIÓN NECESARIA

### 1. BufferBuilder API Changes (CRÍTICO)
**Búsqueda recomendada**: "Minecraft 1.21.1 BufferBuilder API changes getBuilder removed"
**Archivos afectados**: 3 (KeyboardBlock*)

### 2. Block Codec System (CRÍTICO)
**Búsqueda recomendada**: "Minecraft 1.21.1 Block codec MapCodec BaseEntityBlock"
**Archivos afectados**: 1 (PeripheralBlock)

### 3. Capabilities System Rewrite (ALTA PRIORIDAD)
**Búsqueda recomendada**: "NeoForge 21.x Capabilities DataComponentType migration guide"
**Archivos afectados**: 1 (WDDCapability) + referencias en WebDisplays

### 4. Achievement/Criterion API (MEDIA PRIORIDAD)
**Búsqueda recomendada**: "Minecraft 1.21.1 AbstractCriterionTriggerInstance replacement"
**Archivos afectados**: 1 (Criterion)

### 5. ForgeHooksClient Replacement (BAJA PRIORIDAD)
**Búsqueda recomendada**: "NeoForge 21.x ForgeHooksClient getBlockMaterial alternative"
**Archivos afectados**: 1 (ScreenModelLoader)

---

## 📊 ESTADÍSTICAS

### Progreso General
- **Total archivos del mod**: ~150
- **Archivos migrados exitosamente**: ~127 (85%)
- **Archivos con errores**: 15 (10%)
- **Archivos deshabilitados**: 5 (3%)
- **Archivos sin cambios necesarios**: ~3 (2%)

### Errores
- **Errores iniciales**: 100+
- **Errores actuales**: ~100
- **Errores por categoría**:
  - Block/BlockEntity API: 45 errores
  - Rendering (BufferBuilder): 28 errores
  - Renderer API: 13 errores
  - Audio/Data: 11 errores
  - Controls: 3 errores

### Tiempo Estimado
- **Tiempo invertido**: ~3 horas
- **Tiempo para compilación básica**: 4-6 horas
- **Tiempo para funcionalidad completa**: 12-18 horas adicionales
- **Total estimado**: 15-21 horas

---

## 🎯 PRÓXIMOS PASOS INMEDIATOS

### Opción A: Compilación Parcial (Recomendado)
1. Deshabilitar temporalmente los 15 archivos con errores
2. Lograr `BUILD SUCCESSFUL`
3. Probar funcionalidad básica del mod (registries, configs, networking)
4. Ir restaurando archivos uno por uno

### Opción B: Corrección Completa
1. Investigar BufferBuilder API (2 horas)
2. Implementar codec() en PeripheralBlock (1 hora)
3. Corregir 15 archivos secuencialmente (8-12 horas)
4. Restaurar archivos deshabilitados (8-12 horas)

---

## 📝 NOTAS IMPORTANTES

### APIs Eliminadas que Requieren Alternativas
- ✅ `SimpleChannel` → `CustomPacketPayload` + `RegisterPayloadHandlersEvent`
- ✅ `ForgeRegistries` → `BuiltInRegistries`
- ✅ `RegistryObject` → `DeferredHolder`
- ✅ `ForgeMod.BLOCK_REACH` → `Attributes.BLOCK_INTERACTION_RANGE`
- ❌ `Capability/LazyOptional` → `DataComponentType` (pendiente)
- ❌ `AbstractCriterionTriggerInstance` → ??? (pendiente investigación)
- ❌ `ForgeHooksClient.getBlockMaterial()` → ??? (pendiente investigación)
- ❌ `BufferBuilder.getBuilder()` → ??? (pendiente investigación)
- ❌ `PacketDistributor.TargetPoint` → ??? (pendiente investigación)

### Cambios de Estructura
- Constructor del mod ahora recibe `IEventBus` directamente
- EventBus ya no se obtiene con `FMLJavaModLoadingContext.get()`
- Configs se inicializan con `init(IEventBus)`
- Events no tienen `.Post` ni `.phase`
- Blocks requieren método `codec()` para serialización

---

## 🔗 REFERENCIAS ÚTILES

- [NeoForge 21.1 Documentation](https://docs.neoforged.net/docs/1.21.1/)
- [Minecraft 1.21.1 Changelog](https://minecraft.wiki/)
- [NeoForge Migration Guide](https://docs.neoforged.net/docs/migration/)
- [GitHub NeoForge Examples](https://github.com/neoforged)

---

**Última actualización**: 5 de noviembre de 2025  
**Próxima revisión**: Después de completar Fase 1

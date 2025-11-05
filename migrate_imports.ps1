# Script de migración de importaciones de Forge a NeoForge
# Para Minecraft 1.21.1 y NeoForge 21.1.192

$rootPath = "c:\Users\pablo.garcia.s\Documents\GitHub\webdisplays\src\main\java"

# Mapeo de paquetes de Forge a NeoForge
$replacements = @{
    "import net.minecraftforge.api.distmarker." = "import net.neoforged.api.distmarker."
    "import net.minecraftforge.client.event." = "import net.neoforged.neoforge.client.event."
    "import net.minecraftforge.client.model.data." = "import net.neoforged.neoforge.client.model.data."
    "import net.minecraftforge.client.model.geometry." = "import net.neoforged.neoforge.client.model.geometry."
    "import net.minecraftforge.client." = "import net.neoforged.neoforge.client."
    "import net.minecraftforge.common.MinecraftForge;" = "import net.neoforged.neoforge.common.MinecraftForge;"
    "import net.minecraftforge.common.capabilities." = "import net.neoforged.neoforge.common.capabilities."
    "import net.minecraftforge.common.util." = "import net.neoforged.neoforge.common.util."
    "import net.minecraftforge.common." = "import net.neoforged.neoforge.common."
    "import net.minecraftforge.event.entity.item." = "import net.neoforged.neoforge.event.entity.item."
    "import net.minecraftforge.event.entity.player." = "import net.neoforged.neoforge.event.entity.player."
    "import net.minecraftforge.event.level." = "import net.neoforged.neoforge.event.level."
    "import net.minecraftforge.event.server." = "import net.neoforged.neoforge.event.server."
    "import net.minecraftforge.event." = "import net.neoforged.neoforge.event."
    "import net.minecraftforge.eventbus.api." = "import net.neoforged.bus.api."
    "import net.minecraftforge.fml.common.Mod;" = "import net.neoforged.fml.common.Mod;"
    "import net.minecraftforge.fml.common.Optional;" = "import net.neoforged.fml.common.Optional;"
    "import net.minecraftforge.fml.config." = "import net.neoforged.fml.config."
    "import net.minecraftforge.fml.event.lifecycle." = "import net.neoforged.fml.event.lifecycle."
    "import net.minecraftforge.fml.javafmlmod." = "import net.neoforged.fml.javafmlmod."
    "import net.minecraftforge.fml.loading." = "import net.neoforged.fml.loading."
    "import net.minecraftforge.fml.ModList;" = "import net.neoforged.fml.ModList;"
    "import net.minecraftforge.fml.LogicalSide;" = "import net.neoforged.fml.LogicalSide;"
    "import net.minecraftforge.fml.ModLoadingContext;" = "import net.neoforged.fml.ModLoadingContext;"
    "import net.minecraftforge.network.NetworkEvent;" = "import net.neoforged.neoforge.network.NetworkEvent;"
    "import net.minecraftforge.network.NetworkRegistry;" = "import net.neoforged.neoforge.network.NetworkRegistry;"
    "import net.minecraftforge.network.PacketDistributor;" = "import net.neoforged.neoforge.network.PacketDistributor;"
    "import net.minecraftforge.network.simple.SimpleChannel;" = "import net.neoforged.neoforge.network.simple.SimpleChannel;"
    "import net.minecraftforge.registries.DeferredRegister;" = "import net.neoforged.neoforge.registries.DeferredRegister;"
    "import net.minecraftforge.registries.ForgeRegistries;" = "import net.neoforged.neoforge.registries.ForgeRegistries;"
    "import net.minecraftforge.registries.RegistryObject;" = "import net.neoforged.neoforge.registries.RegistryObject;"
    "import net.minecraftforge.server." = "import net.neoforged.neoforge.server."
}

Write-Host "Migrando importaciones de Forge a NeoForge..." -ForegroundColor Cyan
Write-Host "Carpeta raíz: $rootPath" -ForegroundColor Yellow

$javaFiles = Get-ChildItem -Path $rootPath -Filter *.java -Recurse
$filesModified = 0
$totalReplacements = 0

foreach ($file in $javaFiles) {
    $content = Get-Content -Path $file.FullName -Raw -Encoding UTF8
    $originalContent = $content
    $fileReplacements = 0
    
    foreach ($key in $replacements.Keys) {
        $newValue = $replacements[$key]
        $matches = ([regex]::Matches($content, [regex]::Escape($key))).Count
        
        if ($matches -gt 0) {
            $content = $content -replace [regex]::Escape($key), $newValue
            $fileReplacements += $matches
        }
    }
    
    if ($content -ne $originalContent) {
        Set-Content -Path $file.FullName -Value $content -Encoding UTF8 -NoNewline
        $filesModified++
        $totalReplacements += $fileReplacements
        Write-Host "  ✓ $($file.Name): $fileReplacements reemplazos" -ForegroundColor Green
    }
}

Write-Host ""
Write-Host "Migracion completa:" -ForegroundColor Cyan
Write-Host "  Archivos modificados: $filesModified" -ForegroundColor Green
Write-Host "  Total de reemplazos: $totalReplacements" -ForegroundColor Green
Write-Host ""
Write-Host "Ahora ejecuta: gradlew.bat build" -ForegroundColor Yellow

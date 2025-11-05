$ErrorActionPreference = "Stop"# Script para convertir todos los mensajes de Packet a WDPayload

$ErrorActionPreference = "Stop"

function Get-PayloadId {

    param($className)# Función para generar el nombre del ResourceLocation basado en el nombre de la clase

    $name = $className -replace '^(S2C|C2S)Message', ''function Get-PayloadId {

    $name = $name -creplace '([A-Z])', '_$1'    param($className)

    $name = $name.ToLower().TrimStart('_')    

    return $name    # Remover prefijos S2C o C2S y Message

}    $name = $className -replace '^(S2C|C2S)Message', ''

    

function Convert-PayloadFile {    # Convertir CamelCase a snake_case

    param($filePath)    $name = $name -creplace '([A-Z])', '_$1'

        $name = $name.ToLower().TrimStart('_')

    Write-Host "Convirtiendo: $filePath"    

        return $name

    $content = Get-Content $filePath -Raw -Encoding UTF8}

    $fileName = [System.IO.Path]::GetFileNameWithoutExtension($filePath)

    $payloadId = Get-PayloadId $fileName# Función para convertir un archivo

    function Convert-PayloadFile {

    $content = $content -replace 'import net\.neoforged\.neoforge\.network\.NetworkEvent;', 'import net.neoforged.neoforge.network.handling.IPayloadContext;'    param($filePath)

    $content = $content -replace 'import net\.montoyo\.wd\.net\.Packet;', 'import net.montoyo.wd.net.WDPayload;'    

        Write-Host "Convirtiendo: $filePath"

    if ($content -notmatch 'import net\.minecraft\.network\.protocol\.common\.custom\.CustomPacketPayload;') {    

        $content = $content -replace '(package .*?;\s+)', "`$1`nimport net.minecraft.network.protocol.common.custom.CustomPacketPayload;`n"    $content = Get-Content $filePath -Raw -Encoding UTF8

    }    $fileName = [System.IO.Path]::GetFileNameWithoutExtension($filePath)

    if ($content -notmatch 'import net\.minecraft\.resources\.ResourceLocation;') {    $payloadId = Get-PayloadId $fileName

        $content = $content -replace '(import net\.minecraft\.network\.FriendlyByteBuf;\s+)', "`$1import net.minecraft.resources.ResourceLocation;`n"    

    }    # 1. Reemplazar imports

        $content = $content -replace 'import net\.neoforged\.neoforge\.network\.NetworkEvent;', 'import net.neoforged.neoforge.network.handling.IPayloadContext;'

    $content = $content -replace "class $fileName extends Packet", "class $fileName extends WDPayload"    $content = $content -replace 'import net\.montoyo\.wd\.net\.Packet;', 'import net.montoyo.wd.net.WDPayload;'

        

    $typeDeclaration = "`n`t`n`tpublic static final CustomPacketPayload.Type<$fileName> TYPE = `n`t`tnew CustomPacketPayload.Type<>(new ResourceLocation(`"webdisplays`", `"$payloadId`"));`n"    # Agregar imports necesarios si no existen

        if ($content -notmatch 'import net\.minecraft\.network\.protocol\.common\.custom\.CustomPacketPayload;') {

    $content = $content -replace "(class $fileName extends WDPayload \{)", "`$1$typeDeclaration"        $content = $content -replace '(package .*?;\s+)', "`$1`nimport net.minecraft.network.protocol.common.custom.CustomPacketPayload;`n"

        }

    $content = $content -replace '(\s+public\s+' + $fileName + '\s*\([^)]*FriendlyByteBuf[^)]*\)\s*\{\s*)super\(buf\);\s*', '$1'    if ($content -notmatch 'import net\.minecraft\.resources\.ResourceLocation;') {

            $content = $content -replace '(import net\.minecraft\.network\.FriendlyByteBuf;\s+)', "`$1import net.minecraft.resources.ResourceLocation;`n"

    $content = $content -replace 'public\s+void\s+handle\s*\(\s*NetworkEvent\.Context\s+ctx\s*\)', 'public void handle(IPayloadContext context)'    }

        

    $content = $content -replace 'checkClient\(ctx\)', 'isClient(context)'    # 2. Cambiar clase base de Packet a WDPayload

    $content = $content -replace 'checkServer\(ctx\)', 'isServer(context)'    $content = $content -replace "class $fileName extends Packet", "class $fileName extends WDPayload"

        

    $content = $content -replace '\bctx\.', 'context.'    # 3. Agregar constante TYPE después de la declaración de clase

    $content = $content -replace '\(ctx\)', '(context)'    $typeDeclaration = @"

    $content = $content -replace '\bctx\b(?!\.)', 'context'

    	

    $content = $content -replace '\s*context\.setPacketHandled\(true\);\s*', ''	public static final CustomPacketPayload.Type<$fileName> TYPE = 

    		new CustomPacketPayload.Type<>(new ResourceLocation("webdisplays", "$payloadId"));

    if ($content -notmatch '@Override\s+public\s+CustomPacketPayload\.Type') {"@

        $typeMethod = "`n`t`n`t@Override`n`tpublic CustomPacketPayload.Type<? extends CustomPacketPayload> type() {`n`t`treturn TYPE;`n`t}`n"    

        $content = $content -replace '(\s+@Override\s+public\s+void\s+handle\()', "$typeMethod`$1"    $content = $content -replace "(class $fileName extends WDPayload \{)", "`$1$typeDeclaration"

    }    

        # 4. Eliminar super(buf) de constructores que leen de buffer

    $utf8NoBom = New-Object System.Text.UTF8Encoding($false)    $content = $content -replace '(\s+public\s+' + $fileName + '\s*\([^)]*FriendlyByteBuf[^)]*\)\s*\{\s*)super\(buf\);\s*', '$1'

    [System.IO.File]::WriteAllText($filePath, $content, $utf8NoBom)    

        # 5. Reemplazar ctx por context en métodos handle

    Write-Host "  OK: $fileName"    $content = $content -replace 'public\s+void\s+handle\s*\(\s*NetworkEvent\.Context\s+ctx\s*\)', 'public void handle(IPayloadContext context)'

}    

    # 6. Reemplazar checkClient(ctx) por isClient(context)

$clientBoundFiles = Get-ChildItem "src\main\java\net\montoyo\wd\net\client_bound\*.java"    $content = $content -replace 'checkClient\(ctx\)', 'isClient(context)'

Write-Host "Convirtiendo mensajes cliente-bound..."    

foreach ($file in $clientBoundFiles) {    # 7. Reemplazar checkServer(ctx) por isServer(context)

    Convert-PayloadFile $file.FullName    $content = $content -replace 'checkServer\(ctx\)', 'isServer(context)'

}    

    # 8. Reemplazar ctx por context en todo el método

$serverBoundFiles = Get-ChildItem "src\main\java\net\montoyo\wd\net\server_bound\*.java"    $content = $content -replace '\bctx\.', 'context.'

Write-Host "Convirtiendo mensajes servidor-bound..."    $content = $content -replace '\(ctx\)', '(context)'

foreach ($file in $serverBoundFiles) {    $content = $content -replace '\bctx\b(?!\.)', 'context'

    Convert-PayloadFile $file.FullName    

}    # 9. Eliminar ctx.setPacketHandled(true)

    $content = $content -replace '\s*context\.setPacketHandled\(true\);\s*', ''

Write-Host "Conversion completa!"    

    # 10. Agregar método type() si no existe
    if ($content -notmatch '@Override\s+public\s+CustomPacketPayload\.Type') {
        $typeMethod = @"

	
	@Override
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
"@
        # Insertar antes del primer método handle
        $content = $content -replace '(\s+@Override\s+public\s+void\s+handle\()', "$typeMethod`$1"
    }
    
    # Escribir archivo sin BOM
    $utf8NoBom = New-Object System.Text.UTF8Encoding($false)
    [System.IO.File]::WriteAllText($filePath, $content, $utf8NoBom)
    
    Write-Host "  ✓ Convertido: $fileName"
}

# Procesar todos los archivos client_bound
$clientBoundFiles = Get-ChildItem "src\main\java\net\montoyo\wd\net\client_bound\*.java"
$clientCount = $clientBoundFiles.Count
Write-Host ""
Write-Host "Convirtiendo mensajes cliente-bound ($clientCount files)..."
foreach ($file in $clientBoundFiles) {
    Convert-PayloadFile $file.FullName
}

# Procesar todos los archivos server_bound
$serverBoundFiles = Get-ChildItem "src\main\java\net\montoyo\wd\net\server_bound\*.java"
$serverCount = $serverBoundFiles.Count
Write-Host ""
Write-Host "Convirtiendo mensajes servidor-bound ($serverCount files)..."
foreach ($file in $serverBoundFiles) {
    Convert-PayloadFile $file.FullName
}

$totalCount = $clientCount + $serverCount
Write-Host ""
Write-Host "Conversion completa!"
Write-Host "Archivos procesados: $totalCount"

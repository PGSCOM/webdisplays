# Script para eliminar BOM de archivos Java
$rootPath = "c:\Users\pablo.garcia.s\Documents\GitHub\webdisplays\src\main\java"

Write-Host "Eliminando BOM de archivos Java..." -ForegroundColor Cyan

$javaFiles = Get-ChildItem -Path $rootPath -Filter *.java -Recurse
$filesFixed = 0

foreach ($file in $javaFiles) {
    $content = Get-Content -Path $file.FullName -Raw
    
    if ($content -match '^\xEF\xBB\xBF' -or $content[0] -eq [char]0xFEFF) {
        $content = $content.TrimStart([char]0xFEFF)
        $utf8NoBom = New-Object System.Text.UTF8Encoding $false
        [System.IO.File]::WriteAllText($file.FullName, $content, $utf8NoBom)
        $filesFixed++
        Write-Host "  Corregido: $($file.Name)" -ForegroundColor Green
    }
}

Write-Host ""
Write-Host "Archivos corregidos: $filesFixed" -ForegroundColor Green

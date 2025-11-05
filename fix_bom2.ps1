# Script mejorado para eliminar BOM
$rootPath = "c:\Users\pablo.garcia.s\Documents\GitHub\webdisplays\src\main\java"

Write-Host "Eliminando BOM de archivos Java..." -ForegroundColor Cyan

$javaFiles = Get-ChildItem -Path $rootPath -Filter *.java -Recurse
$filesFixed = 0

foreach ($file in $javaFiles) {
    $bytes = [System.IO.File]::ReadAllBytes($file.FullName)
    
    # Detectar UTF-8 BOM (EF BB BF)
    if ($bytes.Length -ge 3 -and $bytes[0] -eq 0xEF -and $bytes[1] -eq 0xBB -and $bytes[2] -eq 0xBF) {
        # Eliminar BOM
        $newBytes = $bytes[3..($bytes.Length-1)]
        [System.IO.File]::WriteAllBytes($file.FullName, $newBytes)
        $filesFixed++
        Write-Host "  Corregido: $($file.Name)" -ForegroundColor Green
    }
}

Write-Host ""
Write-Host "Archivos corregidos: $filesFixed" -ForegroundColor Green

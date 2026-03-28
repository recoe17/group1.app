# Finds Temurin JDK under Program Files and runs the console app (no global PATH required).
$ErrorActionPreference = "Stop"
$adoptiumRoot = "C:\Program Files\Eclipse Adoptium"
$jdkBin = $null
if ($env:JAVA_HOME -and (Test-Path (Join-Path $env:JAVA_HOME "bin\javac.exe"))) {
    $jdkBin = Join-Path $env:JAVA_HOME "bin"
}
if (-not $jdkBin) {
    $jdkDir = Get-ChildItem $adoptiumRoot -Directory -Filter "jdk-*" -ErrorAction SilentlyContinue |
        Sort-Object { $_.Name } -Descending |
        Select-Object -First 1
    if ($jdkDir) {
        $jdkBin = Join-Path $jdkDir.FullName "bin"
    }
}
if (-not $jdkBin -or -not (Test-Path (Join-Path $jdkBin "javac.exe"))) {
    Write-Host "No JDK found. Install Temurin 17+ from https://adoptium.net/ or set JAVA_HOME to your JDK folder." -ForegroundColor Red
    exit 1
}
$env:Path = "$jdkBin;$env:Path"

Set-Location $PSScriptRoot
New-Item -ItemType Directory -Force -Path out | Out-Null
javac -d out src/main/java/sms/*.java
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
java -cp out sms.Main

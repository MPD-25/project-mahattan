@echo off
echo ========================================
echo    PROJECT MANHATTAN - JUEGO ADIVINANZAS
echo ========================================
echo.
mvn compile
echo.
echo Iniciando juego...
echo.
java -cp "target/classes;target/dependency/*" JuegoAdivinaza.Main
pause 
@echo off
echo ==========================================
echo Iniciando Calculadora Jona (Windows)
echo ==========================================

WHERE mvn >nul 2>nul
IF %ERRORLEVEL% NEQ 0 (
    echo [ADVERTENCIA] Maven no detectado en el PATH.
    echo Intentando ejecutar desde codigo fuente...
    java -cp src/main/java com.mycalculator.app.CalculatorGUI
) ELSE (
    echo Compilando proyecto...
    call mvn clean package -DskipTests -q
    echo Ejecutando...
    java -jar target/CalculadoraJona.jar
)

pause

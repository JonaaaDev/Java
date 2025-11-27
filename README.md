# Java Calculator (Hybrid Edition)

Cross-platform calculator with **Hybrid Engine**:
1.  **Java Mode**: Default, works everywhere out of the box.
2.  **C++ Turbo Mode**: Uses JNI for native calculations if compiled.

## Quick Start (Java Mode)
Just run it. It will auto-detect that C++ is missing and use Java.

**Windows:**
```cmd
run_windows.bat
```

**Linux/Mac:**
```bash
./run_unix.sh
```

---

## How to Enable C++ Mode (Optional)

To use the native C++ engine, you need `g++` (GCC) installed.

### 1. Compile the C++ Library

**Linux / macOS:**
```bash
# Create directory for native libs if not exists
mkdir -p native

# Compile
g++ -shared -fPIC -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" \
    src/main/cpp/calculator_native.cpp -o native/libcalculator_native.so
```
*(Note: On Mac, replace `include/linux` with `include/darwin` and extension `.so` with `.dylib`)*

**Windows (MinGW/Git Bash):**
```bash
mkdir native
g++ -shared -I"%JAVA_HOME%\include" -I"%JAVA_HOME%\include\win32" ^
    src\main\cpp\calculator_native.cpp -o native\calculator_native.dll
```

### 2. Run with Library Path
You need to tell Java where the DLL/SO is.

```bash
java -Djava.library.path=native -jar target/CalculadoraJona.jar
```

## Project Structure
*   `src/main/java`: Java Source (GUI & Logic Fallback)
*   `src/main/cpp`: C++ Source (Native Logic)
*   `pom.xml`: Maven Build

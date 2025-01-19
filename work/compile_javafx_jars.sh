#!/bin/bash

# Paths
JAVAFX_SRC="/Library/Java/JavaVirtualMachines/jdk1.8.0_40.jdk/Contents/Home/javafx-src/javafx"
OUTPUT_DIR="$HOME/AutoPrimer3A/javafx_v51"
JAVA_HOME="/Library/Java/JavaVirtualMachines/jdk1.8.0_40.jdk/Contents/Home"
JAVAC="$JAVA_HOME/bin/javac"
JAR="$JAVA_HOME/bin/jar"
JFXRT="$JAVA_HOME/jre/lib/ext/jfxrt.jar"

# Ensure output directory exists
mkdir -p "$OUTPUT_DIR"

# Find all JavaFX source files
echo "Finding JavaFX sources..."
find "$JAVAFX_SRC" -name "*.java" > sources.txt

# Compile the sources
echo "Compiling JavaFX sources..."
$JAVAC -d "$OUTPUT_DIR" -cp "$JFXRT" -XDignore.symbol.file @sources.txt
if [ $? -ne 0 ]; then
    echo "Compilation failed. Please check the errors above."
    rm sources.txt
    exit 1
fi
rm sources.txt
echo "Compilation completed. Classes stored in $OUTPUT_DIR."

# Package into JARs
echo "Packaging compiled classes into JAR files..."
for module in "$JAVAFX_SRC"/*; do
    if [ -d "$module" ]; then
        MODULE_NAME=$(basename "$module")
        MODULE_OUTPUT_DIR="$OUTPUT_DIR/$MODULE_NAME"
        mkdir -p "$MODULE_OUTPUT_DIR"
        $JAR cf "$OUTPUT_DIR/$MODULE_NAME.jar" -C "$OUTPUT_DIR" .
        echo "Created JAR: $OUTPUT_DIR/$MODULE_NAME.jar"
    fi
done

echo "All JARs created successfully in $OUTPUT_DIR."

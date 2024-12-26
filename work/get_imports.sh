#! /bin/bash

cd /Users/bgold/AutoPrimer3A/src/com/github/autoprimer3A

for file in *; do
  if [ -f "$file" ]; then  # Check if it's a regular file
    while read line; do
      grep -h "^import" >> /Users/bgold/AutoPrimer3A/work/imports_in_src_files.txt
    done < "$file"
  fi
done
awk '!x[$0]++' /Users/bgold/AutoPrimer3A/work/imports_in_src_files.txt > /Users/bgold/AutoPrimer3A/work/deduped_imports_in_src_files.txt
#Explanation:
#for file in *; do: This loop iterates over all files and directories in the current directory.
#if [ -f "$file" ]; then: This checks if the current item is a regular file.
#while read line; do: This loop reads each line from the file.
#echo "$line": This prints the line. You can replace this with any other command to process the line as needed.
#done < "$file": This redirects the file content to the while loop's input.
#then, the awk command removes duplicated lines
sort deduped_imports_in_src_files.txt > sorted_deduped_imports_in_src_files.txt #sorts result
sed -E "s/\./\//g" sorted_deduped_imports_in_src_files.txt > replaced_sorted_deduped_imports_in_src_files.txt #replaces periods with forward slash in prep for .java file picking
sed -E "s/\;/\.java/g" replaced_sorted_deduped_imports_in_src_files.txt > prepforcopy.txt # get ready for copy to another directory


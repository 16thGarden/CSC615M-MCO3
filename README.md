# CSC615M MCO3
## File Compression Project

#### If the .jar is broken (skip this part if the file is working)
(in the *project* directory)
1. Run the batch file `RemakeJar.bat` 

**or**

1. Go to the file folder and make sure that there are 5 java files and 1 manifest.txt file
2. Open the command line (same directory)
3. Compile the app by typing `javac *.java`
4. Create the .jar file by typing ``jar cfm CSC615M_File_Compression.jar manifest.txt *.class``
5. You should see the executable jar in the same file directory.

#### To compress a file
1. After opening the executable .jar file, you will see an interface with two input fields for input and output filenames on the upper half for compressing, customize as preferred.
2. click **Compress text**
3. Wait for the program to compress the text (this might take a while depending on the size of the input)
4. Save the result into a .table and .bin file - by clicking click on the **Save to file** button. (this might also take a while depending on the size of the input)

#### To uncompress a file
1. After opening the executable .jar file, you will see an interface with two input fields for input and output filenames on the lower half for uncompressing, customize as preferred.
2. click **Uncompress text**
3. Wait for the program to uncompress the text (this might take a while depending on the size of the input)
4. Save the result into a .text file - by clicking click on the **Save to file** button. (this might also take a while depending on the size of the input)

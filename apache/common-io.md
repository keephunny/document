## FileUtils类
FileUtils.copyFile（File srcFile，File destFile）；
    srcFile：需要拷贝的文件 
    destFile：拷贝后的文件 
    注：可以理解为文件内容的复制，如果拷贝后的文件已存在，将替换已存在文件的内容

FileUtils.copyFileToDirectory（File srcFile,File destDirectory）；
    srcFile：需要拷贝的文件 
    destDirectory：拷贝后的目录 
    注：可以理解为文件的复制，如果拷贝后的目录存在相同名称的文件，将替换之前的文件

FileUtils.copyFile(File srcFile,OutputStream out);
    srcFile：需要拷贝的文件 
    out：输出流 
    注：将文件拷贝到输出流中，个人认为这个比较常用

FileUtils.copyInputStreamToFile(InputStream in,File destFile);
    in：输入流 
    destFile：拷贝后的文件 
    注：将输入流中的数据，拷贝到文件中

FileUtils.forceDelete（File delFile）；
    delFile：删除的文件 
    注：强制删除文件，这里强调一下file类的delete()方法 
    有时候我们使用delete方法删除文件时，会出现文件删除失败，原因有三点： 
    1:文件正在被占用 
    2:没有close输入输出流 
    3:如果是删除目录，必须删除目录下的文件，才能删除目录

FileUtils.contentEquals(File srcFile，File destFile);
    srcFile：源文件 
    destFile :比较的文件 
    注：比较的是文件的内容，返回值是boolean类型

FileUtils.moveToDirectory(File srcFile,File destDirectory);
    srcFile：需要移动的文件 
    destDirectory：移动到的目录 
    注：较常用

FileUtils.readFileToString(File srcFile,String charsetName);
    srcFile：需要读取的文件 
    charsetName：字符编码 UTF-8 
    return String

FileUtils.readLines(File srcFile,String charsetName);
    srcFile：需要读取的文件 
    charsetName：字符编码 UTF-8 
    return List<String>

FileUtils.writeStringToFile(File destFile,String content,String charsetName,Boolean b);
    destFile：需要写入的文件 
    content：写入内容 
    charsetName：字符编码 UTF-8 
    b：默认true就行

File.list(EmptyFileFilter.NOT_EMPTY);
    EmptyFileFilter：过滤文件内容为空的类 
    return String[]; 遍历得到文件名称

File.list(new SuffixFileFilter(String suffix));
    SuffixFileFilter：过滤后缀的类 
    suffix：需要过滤的后缀

FileUtils.lineIterator读取文件行
    File file = new File(basePath + "a.txt");  
    LineIterator li = FileUtils.lineIterator(file);  
    while(li.hasNext()){  
        System.out.println(li.nextLine());  
    }  
    LineIterator.closeQuietly(li);  

NameFileComparator.NAME_COMPARATOR.compare(File srcFile,File distFile);
    比较俩个文件名 
    srcFile：源文件 
    distFile：比较的文件 
    return 0 相同

## FileSystemUtils
获取磁盘空余空间 
    FileSystemUtils.freeSpace("c:\\") / 1024 / 1024 / 1024)
## 
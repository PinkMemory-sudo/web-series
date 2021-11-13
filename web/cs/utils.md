utils



# 加密





## Base64

​	就是讲文件的内容转换成只有64个字符来表示

* Base64就是一种基于64个可打印字符来表示二进制数据的方法,最常见的用于传输8Bit字节码的编码方式之一

* Base64编码就是从二进制到字符的过程

* Base64要求把每三个8Bit的字节转换为四个6Bit的字节（3*8 = 4*6 = 24），然后把6Bit再添两位高位0，组成四个8Bit的字节，



Base64转化步骤

第一步，将每三个字节作为一组，一共是24个二进制位。

第二步，将这24个二进制位分为四组，每个组有6个二进制位。

第三步，在每组前面加两个00，扩展成32个二进制位，即四个字节。

第四步，根据表(0-A,1-B...)，得到扩展后的每个字节的对应符号，这就是Base64的编码值。



也就是说，转换后的字符串理论上将要比原来的长1/3，即每六位会生成一个字节，生成的字节数是32的整数倍







## javax.crypto

Cipher



字段

|                            |          |
| -------------------------- | -------- |
| static int	DECRYPT_MODE | 加密模式 |
| ENCRYPT_MODE               | 解密模式 |
|                            |          |
|                            |          |
|                            |          |
|                            |          |
|                            |          |



|                             |                            |
| --------------------------- | -------------------------- |
| getInstance(String )        | 获得指定密码器             |
| init(int var1, Key var2)    | 初始化密码器模式并出入密钥 |
| byte[] doFinal(byte[] var1) | 加密                       |







csv与Xlsx批量导入

MultipartFile

|                |      |
| -------------- | ---- |
| createTempFile |      |
| transferTo     |      |
|                |      |





使用CsvReader读取和写入csv文件



CsvReader

|               |      |
| ------------- | ---- |
| readHeaders   |      |
| headerCount   |      |
| readRecord    |      |
| currentRecord |      |
| columnCount   |      |
| get(int)      |      |
|               |      |
|               |      |
|               |      |



Workbook接口，用来描述Excel，是sheets/etc的顶级对象，

练习册(工作薄)，

Sheet接口，是Workbook的核心构件，是电子表格工作的地方

最常见的工作表类型是工作表，它用单元格网格表示。工作表单元格可以

包含文本、数字、日期和公式。单元格也可以格式化

Row,代表表格的一行

含有的方法，实现的子类

SXSSFWorkbook

XSSFWorkbook------XLSX

HSSFWorkbook ------XLS



一个工作薄(Workbook)中有多个sheet，一个sheet有多行(Raw)，一行有多个单元格(Cell)

单元格种的数据类型可以是_NONE，NUMERIC，STRING，FORMULA，BLANK，BOOLEAN，ERROR



导入依赖

```xml
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>3.17</version>
        </dependency>
```

读

```java
public static void xlstest() throws IOException, InvalidFormatException {
        // read
        File file = new File("/Users/chenguanlin/Documents/hello.xlsx");
        if (file.exists()) {
            System.out.println(file.length());
        }
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        sheet.forEach(
                row -> {
                    row.forEach(
                            cell -> {
                                CellType cellType = cell.getCellTypeEnum();
                                switch (cellType) {
                                    case STRING:
                                        System.out.println(cell.getStringCellValue());
                                        break;
                                    case NUMERIC:
                                        System.out.println(cell.getNumericCellValue());
                                        break;
                                }
                            }
                    );
                }
        );
    }
```

写

写就是创建工作薄，行，单元格，添加数据，最后创建一个File，将工作薄内容写进File









## Mut...File



# 进制转换

A转二进制



QQ==





1000001



```java
/**
 * 获得文件的编码方式
 *
 * @param file
 * @return
 */
public static String getFileEncoding(File file) throws IOException {
    String encoding="GBK";
    FileInputStream inputStream = new FileInputStream(file);
    // 获得前三个字节
    byte[] head = new byte[3];
    inputStream.read(head);
    if (head[0]==-17 && head[1]==-69 && head[2] ==-65){
        encoding="utf-8";
    }
    return encoding;
}
```




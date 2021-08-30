常用的框架jxl和poi





**EasyExcel**

EasyExcel节约内存，使用简单(直接对象与Excel之间转换)。

EasyExcel能大大减少占用内存的主要原因是在解析Excel时没有将文件数据一次性全部加载到内存中，而是从磁盘上一行行读取数据，逐个解析。



常用注解

@ColumnWidth,设置单元格宽度，平均一个字的快读为3

@ContentRowHeight，设置单元格高度，作用在类上

@HeadRowHeight，表头高度，作用在类上

@DateTimeFormat，转换日期格式，可以用Date写，String读

@ExcelIgnore，作用在字段，不进行Excel转化

@ExcelIgnoreUnannotated， Ignore all unannotated fields

@ExcelProperty，包含value和index等



**write/read**

EasyExcel提供了许多静态的write/read方法，都会返回一个ExcelWriterBuilder
























































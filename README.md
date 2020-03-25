
# 如何编写自己的代码生成器
**我平常开发都会用到代码生成器，本文将介绍如何制作属于自己业务的代码生成工具**

### 代码生成器的作用

**一个新的需求开发涉及到的数据库表有可能有很多张，每个都需要去写增删改查后台管理页面有很多重复的工作并且浪费时间。
具体的作用大家在工作中也都使用过类似的代码生成工具就不在去详细的介绍了，主要有以下几点**

1. 代码风格统一，易维护
2. 提高开发效率
3. 降低bug率从而降低测试成本
4. 无需浪费过多的精力在重复的工作上

------------


### 代码生成器的原理

代码生成器原理其实非常简单，就是使用我们平常写的代码抽取出来一套模板，使用模板绑定数据生成我们需要的代码。

------------


### 接下来一步步去实现一个我们经常用到前端增删改查的生成器

#### 大家想下，没有页面代码生成工具我们是怎么做的？

1. 首先是复制一个以前开发过的列表、新增页面将它复制过来
2. 修改列表页面的标题、展示列、查询域
3. 修改新增页面的表单信息，添加自己的字段
3. 修改列表以及表单用到的增删改查接口
4. 测试是否可用

#### 如何制作生成工具

1. 我们可以写个标准的页面来做我们的模板
2. 将我们平常复制修改的地方修改为占位符
3. 我们可以通过代码将数据库表结构信息或者自己定义的数据信息绑定到模板上生成一个新的页面

### 程序设计

#### 涉及到几大块

1. 制作模板
2. 获取模板绑定的数据源
3. 进行绑定
4. 生成文件

#### 实现代码

生成代码用到的表对象

```java
public class TableBO {
    //标题
    private String title;
    //名称
    private String name;
    //是否可以删除
    private Integer canDelete;
    //是否能新增
    private Integer canAdd;
    //是否可编辑
    private Integer canEdit;
    //是否可查询
    private Integer canQuery;
    //列
    private List<ColumnBO> columns;
}
```
表具体的字段
```java
public class ColumnBO {
     //标题
    private String title;
    //名称
    private String name;
    //字段长度
    private Integer length;
    //ui组件
    private FormUiEnum ui;
    //组件值
    private String uiValue;
    //是否必填
    private boolean isRequired;
    //是否是主键
    private boolean isId;
    //java类型
    private String javaType;
    //是否能在页面新增
    private Boolean canAdd;
}
```
我们用下面表单页面制作模板

效果图
![](http://qny.lanrensoft.cn/20200326011004.png)

源码
```html
<!DOCTYPE html>
<html>

  <head>
    <meta charset="utf-8" />
    <link rel="stylesheet" type="text/css" href="https://www.layuicdn.com/layui/css/layui.css" />
    <script src="https://www.layuicdn.com/layui/layui.js"></script>
  </head>
  <body>         
<blockquote class="layui-elem-quote layui-text">
代码生成展示 
</blockquote>
  
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
  <legend>测试</legend>
</fieldset>
<form class="layui-form layui-form-pane" action="">
  <div class="layui-form-item">
    <label class="layui-form-label">输入框</label>
    <div class="layui-input-inline">
      <input type="text" name="username" lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">日期</label>
      <div class="layui-input-inline">
        <input type="text" name="date" id="date1" autocomplete="off" class="layui-input">
      </div>
    </div>
     
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">单行选择框</label>
    <div class="layui-input-inline">
      <select name="interest" lay-filter="aihao">
        <option value=""></option>
        <option value="0">写作</option>
        <option value="1" selected="">阅读</option>
        <option value="2">游戏</option>
        <option value="3">音乐</option>
        <option value="4">旅行</option>
      </select>
    </div>
  </div>
  <div class="layui-form-item" pane="">
    <label class="layui-form-label">单选框</label>
    <div class="layui-input-block">
      <input type="radio" name="sex" value="男" title="男" checked="">
      <input type="radio" name="sex" value="女" title="女">
      <input type="radio" name="sex" value="禁" title="禁用" disabled="">
    </div>
  </div>
  <div class="layui-form-item layui-form-text">
    <label class="layui-form-label">文本域</label>
    <div class="layui-input-block">
      <textarea placeholder="请输入内容" class="layui-textarea"></textarea>
    </div>
  </div>
  <div class="layui-form-item">
    <button class="layui-btn" lay-submit="" lay-filter="submit">提交</button>
  </div>
</form>    
<script>
layui.use(['form', 'layedit', 'laydate', 'layer'], function(){
  var form = layui.form
  ,layer = layui.layer
  ,layedit = layui.layedit
  ,laydate = layui.laydate;
  laydate.render({
    elem: '#date1'
  });
   
  //监听提交
  form.on('submit(submit)', function(data){
    layer.alert(JSON.stringify(data.field), {
      title: '最终的提交信息'
    })
    return false;
  });
  
});
</script>
  </body>
</html>
```
将可以数据绑定的地方使用占位符代替制作成模板
```html
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8" />
    <link rel="stylesheet" type="text/css" href="https://www.layuicdn.com/layui/css/layui.css" />
    <script src="https://www.layuicdn.com/layui/layui.js"></script>
</head>
<body>
<blockquote class="layui-elem-quote layui-text">
    代码生成展示
</blockquote>

<fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
    <legend>${title}</legend>
</fieldset>
<form class="layui-form layui-form-pane" action="" style="margin: 20px;">
    ${ui}
    <div class="layui-form-item">
        <button class="layui-btn" lay-submit="" lay-filter="submit">提交</button>
    </div>
</form>
<script>
    layui.use(['form', 'layedit', 'laydate', 'layer'], function(){
        var form = layui.form
            ,layer = layui.layer
            ,layedit = layui.layedit
            ,laydate = layui.laydate;
    ${script}
        //监听提交
        form.on('submit(submit)', function(data){
            layer.alert(JSON.stringify(data.field), {
                title: '最终的提交信息'
            })
            return false;
        });

    });
</script>
</body>
</html>
```
我们可以看到把能通过代码动态填充的部分我是用了占位符替代
接下来我通过演示通过程序实现这个代码的编写

#### 主要代码片段展示

表单与数据绑定

> 只展示表单的数据绑定

```java
public class FormTemplateDataBind implements ITemplateDataBind {
    @Override
    public String bindDataToString(TableBO tableBO) {

        //封装模板使用的数据
        HashMap<String, Object> bindData = new HashMap<>();
        bindData.put("title",tableBO.getTitle());
        //表单字段组件内容获取
        bindData.put("ui",new UiTemplateContentHandler().get(tableBO));
        //表单需要的js脚本获取
        bindData.put("script",new ScriptTemplateContentHandler().get(tableBO));
        //绑定模板(这里可以使用三方插件，如 freemarker，beetl等模板工具)
        String formTemplate = BeetlTemplateGenUtils.bindToString(bindData, "formTemplate.html");
        //输出
        return formTemplate;
    }
}
```
表单字段对应组件内容获取

> 只写演示简单的组件

```java
public class UiTemplateContentHandler implements ITemplateContentHandler {
    @Override
    public String get(TableBO tableBO) {
        StringBuilder stringBuilder = new StringBuilder();
        List<ColumnBO> columns = tableBO.getColumns();
        for (ColumnBO column : columns) {
            if(column.getCanAdd()==true){
                switch (column.getUi()){
                    case INPUT:
                        stringBuilder.append(new InputUiHtmlHandler().get(column));
                        break;
                    case DIC:
                        break;
                    case DATE:
                        break;
                    case FILE:
                        break;
                    case RADIO:
                        break;
                    case IMAGE:
                        break;
                    case SELECT:
                        stringBuilder.append(new SelectUiHtmlHandler().get(column));
                        break;
                    case TEXTAREA:
                        stringBuilder.append(new TextAreaUiHtmlHandler().get(column));
                        break;
                    case HTML:
                        break;
                    default:
                        stringBuilder.append(new InputUiHtmlHandler().get(column));
                        break;
                }
            }
        }
        return stringBuilder.toString();
    }
}
```
具体组件内容获取

> 一个文本框的组件是怎么生成的

```java
public class InputUiHtmlHandler implements IUiHtmlHandelr {
    @Override
    public String get(ColumnBO columnBO) {
        String required = "";
        if(columnBO.isRequired()){
            required = "required";
        }
        return "  <div class=\"layui-form-item\">\n" +
                "    <label class=\"layui-form-label\">"+columnBO.getTitle()+"</label>\n" +
                "    <div class=\"layui-input-inline\">\n" +
                "      <input type=\"text\" name=\""+columnBO.getName()+"\" lay-verify=\""+required+"\" placeholder=\"请输入\" autocomplete=\"off\" class=\"layui-input\">\n" +
                "    </div>\n" +
                "  </div>\n";
    }
}
```
编写测试用例
>  代码生成数据来源我是自己定义的数据而没有读取数据库，可以读取数据库，将数据库的信息映射到我们的对象中

```java
public static void main(String[] args) {
		ITemplateDataBind templateDataBind = new FormTemplateDataBind();
		TableBO tableBO = new TableBO();
		tableBO.setTitle("短信模板配置");
		tableBO.setName("smsTemplate");
		tableBO.setCanDelete(0);
		tableBO.setCanAdd(1);
		tableBO.setCanEdit(0);
		tableBO.setCanQuery(1);
		List<ColumnBO> columns = Lists.newArrayList();
		ColumnBO columnBO = new ColumnBO();
		columnBO.setCanAdd(true);
		columnBO.setTitle("模板名称");
		columnBO.setName("templateName");
		columnBO.setLength(20);
		columnBO.setUi(FormUiEnum.INPUT);
		columnBO.setUiValue("");
		columnBO.setRequired(true);
		columnBO.setId(true);
		columnBO.setJavaType("String");
		ColumnBO columnBO2 = new ColumnBO();
		columnBO2.setCanAdd(true);
		columnBO2.setTitle("模板内容");
		columnBO2.setName("templateContent");
		columnBO2.setLength(300);
		columnBO2.setUi(FormUiEnum.TEXTAREA);
		columnBO2.setUiValue("");
		columnBO2.setRequired(true);
		columnBO2.setId(true);
		columnBO2.setJavaType("String");
		ColumnBO columnBO3 = new ColumnBO();
		columnBO3.setCanAdd(true);
		columnBO3.setTitle("模板类型");
		columnBO3.setName("templateType");
		columnBO3.setLength(20);
		columnBO3.setUi(FormUiEnum.SELECT);
		columnBO3.setUiValue("{\"1\":\"短信通知\",\"2\":\"验证码\"}");
		columnBO3.setRequired(true);
		columnBO3.setId(true);
		columnBO3.setJavaType("String");
		columns.add(columnBO);
		columns.add(columnBO2);
		columns.add(columnBO3);
		tableBO.setColumns(columns);
		String string = templateDataBind.bindDataToString(tableBO);
		System.out.printf(string);
	}

```
运行程序输出内容为：
```html
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8" />
    <link rel="stylesheet" type="text/css" href="https://www.layuicdn.com/layui/css/layui.css" />
    <script src="https://www.layuicdn.com/layui/layui.js"></script>
</head>
<body>
<blockquote class="layui-elem-quote layui-text">
    代码生成展示
</blockquote>

<fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
    <legend>短信模板配置</legend>
</fieldset>
<form class="layui-form layui-form-pane" action="" style="margin: 20px;">
      <div class="layui-form-item">
    <label class="layui-form-label">模板名称</label>
    <div class="layui-input-inline">
      <input type="text" name="templateName" lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input">
    </div>
  </div>
   <div class="layui-form-item layui-form-text">
    <label class="layui-form-label">模板内容</label>
    <div class="layui-input-block">
      <textarea name="templateContent" placeholder="请输入内容" class="layui-textarea"></textarea>
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">单行选择框</label>
    <div class="layui-input-inline">
      <select name="templateType" lay-filter="templateType">
        <option value=""></option>
        <option value="1">短信通知</option>
        <option value="2">验证码</option>
      </select>
    </div>
  </div>
    <div class="layui-form-item">
        <button class="layui-btn" lay-submit="" lay-filter="submit">提交</button>
    </div>
</form>
<script>
    layui.use(['form', 'layedit', 'laydate', 'layer'], function(){
        var form = layui.form
            ,layer = layui.layer
            ,layedit = layui.layedit
            ,laydate = layui.laydate;
    
        //监听提交
        form.on('submit(submit)', function(data){
            layer.alert(JSON.stringify(data.field), {
                title: '最终的提交信息'
            })
            return false;
        });

    });
</script>
</body>
</html> 

```
效果图

> 将输出内容存为html打开之后可以看到如下图，我们的添加页面生成成功

![](http://qny.lanrensoft.cn/20200326010937.png)

------------


结语

> 这里只是演示了一个简单的思路，工欲善其事必先利其器，我们平常也会选择好的ide及ide插件来开发，但是遇到一些特殊需求我们可以多思考下有没有更好的方式去实现。
> 源码已提交至github（[https://github.com/lanrensoft-zhangqin/shiqiao-code-generate/tree/master](https://github.com/lanrensoft-zhangqin/shiqiao-code-generate/tree/master)）
/是否可以删除
    private Integer canDelete;
    //是否能新增
    private Integer canAdd;
    //是否可编辑
    private Integer canEdit;
    //是否可查询
    private Integer canQuery;
    //列
    private List<ColumnBO> columns;
}
```
表具体的字段
```java
public class ColumnBO {
     //标题
    private String title;
    //名称
    private String name;
    //字段长度
    private Integer length;
    //ui组件
    private FormUiEnum ui;
    //组件值
    private String uiValue;
    //是否必填
    private boolean isRequired;
    //是否是主键
    private boolean isId;
    //java类型
    private String javaType;
    //是否能在页面新增
    private Boolean canAdd;
}
```
我们用下面表单页面制作模板

效果图
![](http://qny.lanrensoft.cn/20200326011004.png)

源码
```html
<!DOCTYPE html>
<html>

  <head>
    <meta charset="utf-8" />
    <link rel="stylesheet" type="text/css" href="https://www.layuicdn.com/layui/css/layui.css" />
    <script src="https://www.layuicdn.com/layui/layui.js"></script>
  </head>
  <body>         
<blockquote class="layui-elem-quote layui-text">
代码生成展示 
</blockquote>
  
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
  <legend>测试</legend>
</fieldset>
<form class="layui-form layui-form-pane" action="">
  <div class="layui-form-item">
    <label class="layui-form-label">输入框</label>
    <div class="layui-input-inline">
      <input type="text" name="username" lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">日期</label>
      <div class="layui-input-inline">
        <input type="text" name="date" id="date1" autocomplete="off" class="layui-input">
      </div>
    </div>
     
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">单行选择框</label>
    <div class="layui-input-inline">
      <select name="interest" lay-filter="aihao">
        <option value=""></option>
        <option value="0">写作</option>
        <option value="1" selected="">阅读</option>
        <option value="2">游戏</option>
        <option value="3">音乐</option>
        <option value="4">旅行</option>
      </select>
    </div>
  </div>
  <div class="layui-form-item" pane="">
    <label class="layui-form-label">单选框</label>
    <div class="layui-input-block">
      <input type="radio" name="sex" value="男" title="男" checked="">
      <input type="radio" name="sex" value="女" title="女">
      <input type="radio" name="sex" value="禁" title="禁用" disabled="">
    </div>
  </div>
  <div class="layui-form-item layui-form-text">
    <label class="layui-form-label">文本域</label>
    <div class="layui-input-block">
      <textarea placeholder="请输入内容" class="layui-textarea"></textarea>
    </div>
  </div>
  <div class="layui-form-item">
    <button class="layui-btn" lay-submit="" lay-filter="submit">提交</button>
  </div>
</form>    
<script>
layui.use(['form', 'layedit', 'laydate', 'layer'], function(){
  var form = layui.form
  ,layer = layui.layer
  ,layedit = layui.layedit
  ,laydate = layui.laydate;
  laydate.render({
    elem: '#date1'
  });
   
  //监听提交
  form.on('submit(submit)', function(data){
    layer.alert(JSON.stringify(data.field), {
      title: '最终的提交信息'
    })
    return false;
  });
  
});
</script>
  </body>
</html>
```
将可以数据绑定的地方使用占位符代替制作成模板
```html
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8" />
    <link rel="stylesheet" type="text/css" href="https://www.layuicdn.com/layui/css/layui.css" />
    <script src="https://www.layuicdn.com/layui/layui.js"></script>
</head>
<body>
<blockquote class="layui-elem-quote layui-text">
    代码生成展示
</blockquote>

<fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
    <legend>${title}</legend>
</fieldset>
<form class="layui-form layui-form-pane" action="" style="margin: 20px;">
    ${ui}
    <div class="layui-form-item">
        <button class="layui-btn" lay-submit="" lay-filter="submit">提交</button>
    </div>
</form>
<script>
    layui.use(['form', 'layedit', 'laydate', 'layer'], function(){
        var form = layui.form
            ,layer = layui.layer
            ,layedit = layui.layedit
            ,laydate = layui.laydate;
    ${script}
        //监听提交
        form.on('submit(submit)', function(data){
            layer.alert(JSON.stringify(data.field), {
                title: '最终的提交信息'
            })
            return false;
        });

    });
</script>
</body>
</html>
```
我们可以看到把能通过代码动态填充的部分我是用了占位符替代
接下来我通过演示通过程序实现这个代码的编写

#### 主要代码片段展示

表单与数据绑定

> 只展示表单的数据绑定

```java
public class FormTemplateDataBind implements ITemplateDataBind {
    @Override
    public String bindDataToString(TableBO tableBO) {

        //封装模板使用的数据
        HashMap<String, Object> bindData = new HashMap<>();
        bindData.put("title",tableBO.getTitle());
        //表单字段组件内容获取
        bindData.put("ui",new UiTemplateContentHandler().get(tableBO));
        //表单需要的js脚本获取
        bindData.put("script",new ScriptTemplateContentHandler().get(tableBO));
        //绑定模板(这里可以使用三方插件，如 freemarker，beetl等模板工具)
        String formTemplate = BeetlTemplateGenUtils.bindToString(bindData, "formTemplate.html");
        //输出
        return formTemplate;
    }
}
```
表单字段对应组件内容获取

> 只写演示简单的组件

```java
public class UiTemplateContentHandler implements ITemplateContentHandler {
    @Override
    public String get(TableBO tableBO) {
        StringBuilder stringBuilder = new StringBuilder();
        List<ColumnBO> columns = tableBO.getColumns();
        for (ColumnBO column : columns) {
            if(column.getCanAdd()==true){
                switch (column.getUi()){
                    case INPUT:
                        stringBuilder.append(new InputUiHtmlHandler().get(column));
                        break;
                    case DIC:
                        break;
                    case DATE:
                        break;
                    case FILE:
                        break;
                    case RADIO:
                        break;
                    case IMAGE:
                        break;
                    case SELECT:
                        stringBuilder.append(new SelectUiHtmlHandler().get(column));
                        break;
                    case TEXTAREA:
                        stringBuilder.append(new TextAreaUiHtmlHandler().get(column));
                        break;
                    case HTML:
                        break;
                    default:
                        stringBuilder.append(new InputUiHtmlHandler().get(column));
                        break;
                }
            }
        }
        return stringBuilder.toString();
    }
}
```
具体组件内容获取

> 一个文本框的组件是怎么生成的

```java
public class InputUiHtmlHandler implements IUiHtmlHandelr {
    @Override
    public String get(ColumnBO columnBO) {
        String required = "";
        if(columnBO.isRequired()){
            required = "required";
        }
        return "  <div class=\"layui-form-item\">\n" +
                "    <label class=\"layui-form-label\">"+columnBO.getTitle()+"</label>\n" +
                "    <div class=\"layui-input-inline\">\n" +
                "      <input type=\"text\" name=\""+columnBO.getName()+"\" lay-verify=\""+required+"\" placeholder=\"请输入\" autocomplete=\"off\" class=\"layui-input\">\n" +
                "    </div>\n" +
                "  </div>\n";
    }
}
```
编写测试用例
>  代码生成数据来源我是自己定义的数据而没有读取数据库，可以读取数据库，将数据库的信息映射到我们的对象中

```java
public static void main(String[] args) {
		ITemplateDataBind templateDataBind = new FormTemplateDataBind();
		TableBO tableBO = new TableBO();
		tableBO.setTitle("短信模板配置");
		tableBO.setName("smsTemplate");
		tableBO.setCanDelete(0);
		tableBO.setCanAdd(1);
		tableBO.setCanEdit(0);
		tableBO.setCanQuery(1);
		List<ColumnBO> columns = Lists.newArrayList();
		ColumnBO columnBO = new ColumnBO();
		columnBO.setCanAdd(true);
		columnBO.setTitle("模板名称");
		columnBO.setName("templateName");
		columnBO.setLength(20);
		columnBO.setUi(FormUiEnum.INPUT);
		columnBO.setUiValue("");
		columnBO.setRequired(true);
		columnBO.setId(true);
		columnBO.setJavaType("String");
		ColumnBO columnBO2 = new ColumnBO();
		columnBO2.setCanAdd(true);
		columnBO2.setTitle("模板内容");
		columnBO2.setName("templateContent");
		columnBO2.setLength(300);
		columnBO2.setUi(FormUiEnum.TEXTAREA);
		columnBO2.setUiValue("");
		columnBO2.setRequired(true);
		columnBO2.setId(true);
		columnBO2.setJavaType("String");
		ColumnBO columnBO3 = new ColumnBO();
		columnBO3.setCanAdd(true);
		columnBO3.setTitle("模板类型");
		columnBO3.setName("templateType");
		columnBO3.setLength(20);
		columnBO3.setUi(FormUiEnum.SELECT);
		columnBO3.setUiValue("{\"1\":\"短信通知\",\"2\":\"验证码\"}");
		columnBO3.setRequired(true);
		columnBO3.setId(true);
		columnBO3.setJavaType("String");
		columns.add(columnBO);
		columns.add(columnBO2);
		columns.add(columnBO3);
		tableBO.setColumns(columns);
		String string = templateDataBind.bindDataToString(tableBO);
		System.out.printf(string);
	}

```
运行程序输出内容为：
```html
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8" />
    <link rel="stylesheet" type="text/css" href="https://www.layuicdn.com/layui/css/layui.css" />
    <script src="https://www.layuicdn.com/layui/layui.js"></script>
</head>
<body>
<blockquote class="layui-elem-quote layui-text">
    代码生成展示
</blockquote>

<fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
    <legend>短信模板配置</legend>
</fieldset>
<form class="layui-form layui-form-pane" action="" style="margin: 20px;">
      <div class="layui-form-item">
    <label class="layui-form-label">模板名称</label>
    <div class="layui-input-inline">
      <input type="text" name="templateName" lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input">
    </div>
  </div>
   <div class="layui-form-item layui-form-text">
    <label class="layui-form-label">模板内容</label>
    <div class="layui-input-block">
      <textarea name="templateContent" placeholder="请输入内容" class="layui-textarea"></textarea>
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">单行选择框</label>
    <div class="layui-input-inline">
      <select name="templateType" lay-filter="templateType">
        <option value=""></option>
        <option value="1">短信通知</option>
        <option value="2">验证码</option>
      </select>
    </div>
  </div>
    <div class="layui-form-item">
        <button class="layui-btn" lay-submit="" lay-filter="submit">提交</button>
    </div>
</form>
<script>
    layui.use(['form', 'layedit', 'laydate', 'layer'], function(){
        var form = layui.form
            ,layer = layui.layer
            ,layedit = layui.layedit
            ,laydate = layui.laydate;
    
        //监听提交
        form.on('submit(submit)', function(data){
            layer.alert(JSON.stringify(data.field), {
                title: '最终的提交信息'
            })
            return false;
        });

    });
</script>
</body>
</html> 

```
效果图

> 将输出内容存为html打开之后可以看到如下图，我们的添加页面生成成功

![](http://qny.lanrensoft.cn/20200326010937.png)

------------


结语

> 这里只是演示了一个简单的思路，工欲善其事必先利其器，我们平常也会选择好的ide及ide插件来开发，但是遇到一些特殊需求我们可以多思考下有没有更好的方式去实现。
源码已提交至github（[https://github.com/lanrensoft-zhangqin/shiqiao-code-generate/tree/master](https://github.com/lanrensoft-zhangqin/shiqiao-code-generate/tree/master)）

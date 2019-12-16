# UiDelegater

### P1

> 做Android的话经常会写很多shape来实现圆角，渐变等效果。特别是UI效果不统一的时候，这种情况更加明显，要是仅仅为了改变个圆角尺寸又写一个xml真的是脑袋都大了。也不清楚官方为啥不给一个。
> 
> 下面是两种解决方案，本质上都是通过属性直接设置，减少了书写xml，这样的话，如果样式统一，就可以采用style框起来，如果不统一，就直接在View上设置属性，非常方便。<br>
> 注入实现方案：[https://github.com/Surine/UiDelegater](https://github.com/Surine/UiDelegater)<br>
> 自定义View实现方案：[https://github.com/Surine/LazyAndroid](https://github.com/Surine/LazyAndroid)
> 
> 此外，要特别说明一下，这两个库，目前仅仅是做学习使用，如果你想自己实现，可以参考一下我写的，相对来说还算通俗易懂，大神写的太难懂了…… 我本质上是喜欢用自己做的东西，除非像OkHttp，Retrofit，Glide这种大型的库会采用第三方，其他的都会自己写顺便学习思想。大神的库已经很多star了，我就不再这里班门弄斧了，如果有同学想采用这两种技术方案可以搜对应的库，这里我写的[UiDelegater](https://github.com/Surine/UiDelegater)的思想是基于BackgroundLibrary 这个库[https://github.com/JavaNoober/BackgroundLibrary](https://github.com/JavaNoober/BackgroundLibrary)


UiDelegater是一个使用Ui注入的方式来实现Shape效果的库，主要灵感是来自于[BackgroundLibrary](https://github.com/JavaNoober/BackgroundLibrary)这个库，思路也是一样的，单纯是为了学习这种形式才写的这个库，真正使用的时候还是推荐自定义View，相比Ui注入更安全易管理一些，当然这两种方式也有很多轮子，我这边也是有写过，但是就不发JCenter了，如果自己写的话可以给您提供一个参考。

### P2
#### 使用条件
项目代码目前是在**androidx.AppCompatActivity**条件下运行测试的，原理也是基于**AppCompatActivity**的UI委托方式实现，v7包也可以支持的。所以这就要求您的Activity必须继承自**AppCompatActivity**同理如果您使用其他组件，如Fragment等也可以支持。

#### 使用方法
实现注入效果需要继承自**UisBaseActivity **，我们项目中一般会有**BaseActivity**，直接令他继承自**UisBaseActivity**即可，当然如果实在没法继承，可以调用 **Uix.init**方法来初始化，注意一定要super.onCreate(savedInstanceState)之前。

```java
@Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Uix.init(this);
        super.onCreate(savedInstanceState);
    }
```

初始化之后，使用就很简单了，只需要给我们常见的控件加属性即可。

```xml
<Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="40dp"
        android:layout_marginLeft="20dp"
        android:layout_marginTop="20dp"
        android:tag="color:#fff|radius:20dp"
        android:text="@string/app_name"
        android:textColor="#fff"
        app:color="@color/colorPrimary"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:pressColor="@color/colorPrimary"
        app:radius="40dp"
        app:startColor="#B86A6A"
        app:centerColor="#6E0A0A"
        app:endColor="#DCC1C1"
        app:orientation="BL_TR"
        app:gradientRadius="40dp"
        app:gradient="RADIAL"
        app:strokeColor="@color/colorAccent"
        app:strokeWidth="2dp" />
```

如上述View, 像**app:color**就是设置shape背景色， **app:radius**就是设置半径，但是作为Android的一个规则，上述自定义的代码不会有补全提示（好奇约束布局的提示为啥好用……，可能是父布局的原因）

#### 代码补全

在这里大佬的库采用的是Live Template的方式来配置补全，是一种比较优雅的解决方案了。我想了想也没啥替代方案了，不过后来还是开发了一个简单的插件[https://github.com/Surine/uix_xml_helper](https://github.com/Surine/uix_xml_helper) ，通过这个插件，可以快速生成想要的代码。

插件里会内置UiDelegater的相关属性，通过填写他的value可以直接在光标处的下一行插入添加的内容。
当然只会添加写了值的属性，这个插件还比较弱小，较为简单。后续可能更新。具体请参考他的README，如果你有更好的解决方案，可以联系我。

![image.png](https://cdn.nlark.com/yuque/0/2019/png/276442/1576239375942-c2006cbd-48f9-4af0-b2be-dd565fc1ef98.png#align=left&display=inline&height=486&name=image.png&originHeight=1598&originWidth=1694&size=284016&status=done&style=none&width=515)


#### 预览
预览的话，大佬是使用自定义View辅助预览的，我觉得这也是没办法的选择，毕竟我们是通过注入的形式实现的，As也没法完成预览，所以这里我没有添加大量的自定义View（仅仅是为预览所用），具体效果可通过实际测试效果为准。


#### 高级功能
虽然Android规定一个Activity只能用一个UI代理，但是我们可以在代理中做很多事情，所以这里我用**StrategyManager**管理Ui处理策略，默认是三个，字体，主题和Shape的，其中前两个为预留的，目前仅实现ViewStrategy，我们也可以实现自己的策略，添加到管理器里即可实现效果
```java
 private StrategyManager() {
        uiStrategies.add(new FontStrategy());
        uiStrategies.add(new ThemeStrategy());
        uiStrategies.add(new ViewStrategy());
    }
```

具体实现步骤如下；

1. 实现**UiStrategy**这个接口，这个接口中仅有一个方法，apply方法就是我们配置效果的方法,其中里面有三个参数，一个是context，一个是attrs，一个就是目标view，attrs是view所携带的属性（xml文件中写的），这就是我为什么能从app:xxx中拿到属性的原因。

```java
public interface UiStrategy {

    /**
     * apply the ui effect
     *
     * @param context
     * @param attrs   UI attributes
     * @param view    target view
     * @return view modified view
     */
    View apply(Context context, AttributeSet attrs, View view);
}
```

2. 一般会对目标View进行类型判断，防止不正确的强转导致的崩溃，这里判断如果是TextView的话，就获取当前要设置的字体进行设置，最后return view，view还是传入的view，只是拥有了typeface
```java
public class TestUiStrategy implements UiStrategy {
    public static String TTF = "lx.ttf";

    @Override
    public View apply(Context context, AttributeSet attrs, View view) {
        if (view instanceof TextView) {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), TTF);
            ((TextView) view).setTypeface(typeface);
        }
        return view;
    }
}
```

3. 最后要把写好的类注册一下。下面是BaseActivity的代码，通过addUiStrategies方法加入新的UI策略，这样在运行的时候就可以达到全局换字体的效果啦。

```java
  @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //必须在super.onCreate(savedInstanceState);前添加自定义策略
        StrategyManager.getInstance().addUiStrategies(new TestUiStrategy());
        super.onCreate(savedInstanceState);
        setContentView(layoutId());
        onInit();
    }
```

4. 上述的小栗子换字体一般是需要重启的，（recreate()不推荐用），包括换主题，后期我会研究一下，支持在Uix策略里。
4. 更多玩法，可以自己探究一下。

### P3
还是那句话，我喜欢自己写库，但是不喜欢重复造轮子。但凡有重复的内容我也会加入新的想法和方案来 与（bi）众（mian）不（xian）同（yi），如果有同学想使用或者学习的话非常欢迎，有问题可以提出一起交流，虽然跟大佬的比差远，但这也不是个一蹴而就的事情，后期可能维护的话，继续丰富吧。

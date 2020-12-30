package com.aitao.myblog.utils;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

import javax.swing.text.html.HTMLDocument;
import java.util.*;

/**
 * @Author : AiTao
 * @Create : 2020/12/17 15:19
 * @Description : MarkDown工具类
 */
@Component
public class MarkDownUtils {
    /**
     * markdown转换成HTML
     *
     * @param content markdown格式内容
     * @return 返回转换后的HTML格式数据
     */
    public static String markDownToHtml(String content) {
        //1、创建MarkDown解析对象
        Parser parser = Parser.builder().build();
        //2、解析数据
        Node document = parser.parse(content);
        //3、创建html渲染对象，解析Node
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        //4、将解析的数据转换成HTML格式字符串
        return renderer.render(document);
    }

    /**
     * 增加扩展解析功能(标题多花点，表格生成)
     * MarkDown转换成Html
     *
     * @param content markdown格式内容
     * @return 返回转换后的HTML格式数据
     */
    public static String markDownToHtmlExtension(String content) {
        //h1~h6标题标签生成id属性
        Set<Extension> headingExtension = Collections.singleton(HeadingAnchorExtension.create());
        //转换table标题的HTML
        List<Extension> tableExtension = Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder().extensions(tableExtension).build();
        Node document = parser.parse(content);
        //创建html渲染对象
        HtmlRenderer htmlRenderer = HtmlRenderer.builder().extensions(headingExtension).extensions(tableExtension).attributeProviderFactory(new AttributeProviderFactory() {
            @Override
            public AttributeProvider create(AttributeProviderContext attributeProviderContext) {
                return new CustomAttributeProvider();
            }
        }).build();
        return htmlRenderer.render(document);
    }

    /**
     * 处理标题的属性
     */
    private static class CustomAttributeProvider implements AttributeProvider {
        /**
         * 设置属性
         *
         * @param node       节点对象
         * @param tagName    标签名
         * @param attributes 属性Map
         */
        @Override
        public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
            //改变a标签的target属性值为_blank
            if (node instanceof Link) {
                attributes.put("target", "_blank");
            }
            //如果是一个表格标签,添加类名
            if (node instanceof TableBlock) {
                attributes.put("class", "ui celled table");
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(MarkDownUtils.markDownToHtmlExtension("### 锁\n" +
                "\n" +
                "#### synchronized⭐\n" +
                "\n" +
                "每个 Java 对象都有一个关联的 monitor，JVM 会根据 monitor 的状态进行加解锁的判断，monitor 在被释放前不能被其他线程获取。\n" +
                "\n" +
                "同步块使用 `monitorenter` 和 `monitorexit` 字节码指令获取和释放 monitor。这两个指令都需要一个引用类型的参数指明锁对象，对于普通方法，锁是当前实例对象；对于静态方法，锁是当前类的 Class 对象；对于方法块，锁是 synchronized 括号里的对象。\n" +
                "\n" +
                "执行 `monitorenter` 指令时，首先尝试获取对象锁。如果这个对象没有被锁定，或当前线程已经持有锁，就把锁的计数器加 1，执行 `monitorexit` 指令时会将锁计数器减 1。一旦计数器为 0 锁随即就被释放。\n" +
                "\n" +
                "假设有两个线程 A、B 竞争锁，当 A 竞争到锁时会将 monitor 中的 owner 设置为 A，把 B 阻塞并放到等待资源的 ContentionList 队列。ContentionList 中的部分线程会进入 EntryList，EntryList 中的线程会被指定为 OnDeck 竞争候选者，如果获得了锁资源将进入 Owner 状态，释放锁后进入 !Owner 状态。\n" +
                "\n" +
                "synchronized 修饰的同步块是可重入的，并且持有锁的线程释放锁前会阻塞其他线程。持有锁是一个重量级的操作，Java 线程是映射到操作系统的内核线程上的，如果要阻塞或唤醒一条线程，需要用户态到核心态的转换。\n" +
                "\n" +
                "**不公平性**\n" +
                "\n" +
                "所有收到锁请求的线程首先自旋，如果通过自旋也没有获取锁将被放入 ContentionList，该做法对于已经进入队列的线程不公平。\n" +
                "\n" +
                "为了防止 ContentionList 队列尾部的元素被大量线程 CAS 访问影响性能，Owner 线程会在释放锁时将队列的部分线程移动到 EntryList 并指定某个线程为 OnDeck 线程。\n" +
                "\n" +
                "---\n" +
                "\n" +
                "#### 锁优化策略⭐\n" +
                "\n" +
                "JDK 6 对 synchronized 做了很多优化，引入了自适应自旋、锁消除、锁粗化、偏向锁和轻量级锁等提高锁的效率，锁一共有 4 个状态，级别从低到高依次是：无锁、偏向锁、轻量级锁和重量级锁，状态会随竞争升级，但不能降级。\n" +
                "\n" +
                "---\n" +
                "\n" +
                "**自适应自旋**\n" +
                "\n" +
                "许多锁定时间很短，为了这段时间去挂起和恢复线程并不划算。如果机器有多个处理器核心，可以让后面请求锁的线程稍等一会，但不放弃处理器的执行时间，看看锁是否很快会被释放，这就是自旋锁。\n" +
                "\n" +
                "自旋锁在 JDK1.4 引入，默认关闭，在 JDK6 默认开启。如果自旋超过限定次数仍然没有成功，就应挂起线程，自旋默认限定次数是 10。\n" +
                "\n" +
                "JDK6 对自旋锁进行了优化，自旋时间不再固定，而是由前一次的自旋时间及锁拥有者的状态决定。\n" +
                "\n" +
                "如果在同一个锁上，自旋刚刚成功获得过锁且持有锁的线程正在运行，虚拟机会认为这次自旋也很可能成功，允许自旋持续更久。如果自旋很少成功，以后获取锁时将可能直接省略掉自旋，避免浪费处理器资源。\n" +
                "\n" +
                "----\n" +
                "\n" +
                "**锁消除**\n" +
                "\n" +
                "锁消除指即时编译器对检测到不可能存在共享数据竞争的锁进行消除。如果堆上的所有数据都只被一个线程访问，就当作栈上的数据对待，认为它们是线程私有的而无须同步。\n" +
                "\n" +
                "---\n" +
                "\n" +
                "**锁粗化**\n" +
                "\n" +
                "原则上需要将同步块的作用范围限制得尽量小，只在共享数据的实际作用域进行同步，使等待锁的线程尽快拿到锁。\n" +
                "\n" +
                "但如果一系列的连续操作都对同一个对象反复加锁和解锁，即使没有线程竞争也会导致不必要的性能消耗。因此如果虚拟机探测到有一串零碎的操作都对同一个对象加锁，会把同步范围扩展到整个操作序列的外部。\n" +
                "\n" +
                "---\n" +
                "\n" +
                "**偏向锁**\n" +
                "\n" +
                "偏向锁是为了在没有竞争的情况下减少锁开销，锁会偏向于第一个获得它的线程，如果在执行过程中锁一直没有被其他线程获取，则持有偏向锁的线程将不需要同步。\n" +
                "\n" +
                "当锁对象第一次被线程获取时，虚拟机会将对象头中的偏向模式设为 1，同时使用 CAS 把获取到锁的线程 ID 记录在对象的 Mark Word 中。如果 CAS 成功，持有偏向锁的线程以后每次进入锁相关的同步块都不再进行任何同步操作。\n" +
                "\n" +
                "一旦有其他线程尝试获取锁，偏向模式立即结束，根据锁对象是否处于锁定状态决定是否撤销偏向，后续同步按照轻量级锁的步骤执行。\n" +
                "\n" +
                "---\n" +
                "\n" +
                "**轻量级锁**\n" +
                "\n" +
                "轻量级锁是为了在没有竞争的前提下减少重量级锁的性能消耗。\n" +
                "\n" +
                "在代码即将进入同步块时，如果同步对象没有被锁定，虚拟机将在当前线程的栈帧中建立一个锁记录空间，存储锁对象目前 Mark Word 的拷贝。然后使用 CAS 尝试把对象的 Mark Word 更新为指向锁记录的指针，如果更新成功代表该线程拥有了锁，锁标志位将转变为 00，表示轻量级锁状态。\n" +
                "\n" +
                "如果更新失败就意味着存在线程竞争。虚拟机检查对象的 Mark Word 是否指向当前线程的栈帧，如果是则说明当前线程已经拥有了锁，直接进入同步块继续执行，否则说明锁已被其他线程抢占。如果出现两条以上线程竞争锁，轻量级锁将膨胀为重量级锁，锁标志状态变为 10，此时Mark Word 存储的就是指向重量级锁的指针，后面等待锁的线程将阻塞。\n" +
                "\n" +
                "解锁同样通过 CAS 进行，如果对象 Mark Word 仍然指向线程的锁记录，就用 CAS 把对象当前的 Mark Word 和线程复制的 Mark Word 替换回来。假如替换成功同步过程就完成了，否则说明有其他线程尝试过获取该锁，就要在释放锁的同时唤醒被挂起的线程。\n" +
                "\n" +
                "---\n" +
                "\n" +
                "**偏向锁、轻量级锁和重量级锁的区别**\n" +
                "\n" +
                "偏向锁的优点是加解锁不需要额外消耗，和执行非同步方法比仅存在纳秒级差距，适用只有一个线程访问同步代码块的场景。\n" +
                "\n" +
                "轻量级锁的优点是程序响应速度快，缺点是如果线程始终得不到锁会自旋消耗 CPU，适用追求响应时间、同步代码块执行快的场景。\n" +
                "\n" +
                "重量级锁的优点是线程竞争不使用自旋不消耗CPU，缺点是线程会阻塞，响应时间慢，适应追求吞吐量、同步代码块执行慢的场景。\n" +
                "\n" +
                "---\n" +
                "\n" +
                "#### Lock\n" +
                "\n" +
                "Lock 是 juc 包的顶层接口，摆脱了语言特性束缚，在类库层面实现同步，利用了 volatile 的可见性。\n" +
                "\n" +
                "重入锁 ReentrantLock 是 Lock 最常见的实现，与 synchronized 一样可重入，不过它增加了一些功能：\n" +
                "\n" +
                "- **等待可中断： **持有锁的线程长期不释放锁时，正在等待的线程可以选择放弃等待而处理其他事情。\n" +
                "- **公平锁：** synchronized 是非公平的，ReentrantLock 默认是非公平的，可以通过构造方法指定公平锁。\n" +
                "- **锁绑定多个条件：** 一个 ReentrantLock 可以同时绑定多个 Condition。synchronized 中锁对象的 `wait` 跟 `notify` 只可以实现一个隐含条件，而 ReentrantLock 可以调用 `newCondition` 创建多个条件。\n" +
                "\n" +
                "一般优先考虑 synchronized：① synchronized 是语法层面的同步，足够简单。② Lock 必须手动在 finally 释放锁，而synchronized 可以由 JVM 来确保即使出现异常也能正常释放锁。③ 尽管 JDK5 时 ReentrantLock 的性能优于 synchronized，但 JDK6 锁优化后二者的性能基本持平。 JVM 更可能针对synchronized 优化，因为 JVM 可以在线程和对象的元数据中记录锁的相关信息。\n" +
                "\n" +
                "---\n" +
                "\n" +
                "**ReentrantLock 的可重入实现**\n" +
                "\n" +
                "以非公平锁为例，通过 `nonfairTryAcquire` 方法获取锁，如果是持有锁的线程再次请求则增加同步状态值并返回 true。\n" +
                "\n" +
                "释放同步状态时将减少同步状态值。如果锁被获取了 n 次，那么前 n-1 次 `tryRelease` 方法必须都返回 fasle，只有同步状态完全释放才能返回 true，并将锁占有线程设置为null。\n" +
                "\n" +
                "公平锁使用 `tryAcquire` 方法，该方法与非公平锁的区别是：判断条件中多了对同步队列中当前节点是否有前驱节点的判断，如果该方法返回 true 表示有线程比当前线程更早请求锁，因此需要等待前驱线程。\n" +
                "\n" +
                "---\n" +
                "\n" +
                "#### 读写锁\n" +
                "\n" +
                "读写锁在同一时刻允许多个读线程访问，在写线程访问时，所有的读写线程均阻塞。\n" +
                "\n" +
                "读写锁依赖 AQS 实现，在一个 int 变量上维护读线程和写线程的状态，将变量切分成了两个部分，高 16 位表示读，低 16 位表示写。\n" +
                "\n" +
                "写锁是可重入排他锁，如果当前线程已经获得了写锁则增加写状态，如果当前线程在获取写锁时，读锁已经被获取或者该线程不是已经获得写锁的线程则等待。写锁的释放与 ReentrantLock 的释放类似，每次释放减少写状态，当写状态为 0 时表示写锁已被释放。\n" +
                "\n" +
                "读锁是可重入共享锁，能够被多个线程同时获取。如果当前线程已经获取了读锁，则增加读状态。如果当前线程在获取读锁时，写锁已被其他线程获取则进入等待。读锁每次释放会减少读状态，减少的值是 `1<<16`，读锁的释放是线程安全的。\n" +
                "\n" +
                "**锁降级**指把持住当前写锁再获取读锁，随后释放先前拥有的写锁。\n" +
                "\n" +
                "锁降级中读锁的获取是必要的，这是为了保证数据可见性，如果当前线程不获取读锁而直接释放写锁，假设另一个线程 A 获取写锁修改数据，当前线程无法感知线程 A 的数据更新。\n" +
                "\n" +
                "---\n" +
                "\n" +
                "#### AQS\n" +
                "\n" +
                "AQS 队列同步器是用来构建锁或其他同步组件的基础框架，使用一个 volatile int 变量作为共享同步状态，如果线程获取状态失败，则进入同步队列等待；如果成功就执行临界区代码，释放状态时会通知同步队列中的等待线程。\n" +
                "\n" +
                "同步器的主要使用方式是继承，子类通过继承同步器并实现它的抽象方法来管理同步状态，对同步状态进行更改需要使用同步器提供的 3个方法 `getState`、`setState` 和 `compareAndSetState` ，它们保证状态改变是安全的。\n" +
                "\n" +
                "每当有新线程请求同步状态时都会进入一个等待队列，等待队列通过双向链表实现，线程被封装在链表的 Node 节点中，Node 的等待状态包括：CANCELLED（线程已取消）、SIGNAL（线程需要唤醒）、CONDITION （线程正在等待）、PROPAGATE（后继节点会传播唤醒操作，只作用于共享模式）。\n" +
                "\n" +
                "---\n" +
                "\n" +
                "**两种模式**\n" +
                "\n" +
                "**独占模式**下锁只会被一个线程占用，其他线程必须等持有锁的线程释放锁后才能获取锁。\n" +
                "\n" +
                "获取同步状态时，调用 `acquire` 方法的 `tryAcquire` 方法安全地获取同步状态，获取失败的线程会被构造同步节点并通过 `addWaiter` 方法加入到同步队列的尾部，在队列中自旋。之后调用 `acquireQueued` 方法使得节点以死循环的方式获取同步状态，如果获取不到则阻塞，被阻塞线程的唤醒依靠前驱节点的出队或中断。后继节点的线程被唤醒后需要检查自己的前驱节点是否是头节点，目的是维护同步队列的 FIFO 原则，节点之间在循环检查的过程中基本不通信，而是简单判断自己的前驱是否为头节点。\n" +
                "\n" +
                "释放同步状态时，同步器调用 `tryRelease` 方法释放同步状态，然后调用 `unparkSuccessor` 方法唤醒头节点的后继节点，使后继节点重新尝试获取同步状态。\n" +
                "\n" +
                "**共享模式**下多个线程可以获取同一个锁。\n" +
                "\n" +
                "获取同步状态时，调用 `acquireShared` 方法的 `tryAcquireShared` 方法，返回值为 int 类型，值不小于 0 表示能获取同步状态。\n" +
                "\n" +
                "释放同步状态时，调用 `releaseShared` 方法，释放后会唤醒后续处于等待状态的节点。它和独占式的区别在于 `tryReleaseShared` 方法必须确保同步状态安全释放，通过循环 CAS 保证。"));
    }
}

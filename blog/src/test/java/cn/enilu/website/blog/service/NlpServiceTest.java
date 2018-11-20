package cn.enilu.website.blog.service;

import cn.enilu.website.BaseApplicationStartTest;
import org.jsoup.Jsoup;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * Created  on  2018/7/19 0019
 * NlpServiceTest
 *
 * @author enilu
 */
public class NlpServiceTest  extends BaseApplicationStartTest {
    @Autowired
    private NlpService nlpService;
    String title = "iphone手机出现“白苹果”原因及解决办法，用苹果手机的可以看下";
    String content = "如果下面的方法还是没有解决你的问题建议来我们门店看下成都市锦江区红星路三段99号银石广场24层01室。";

    @Test
    public void tag() {
        Set list = nlpService.tag(title,content);
        for(int i=0;i<list.size();i++){

        }
    }

    @Test
    public void category() {
        String category = nlpService.category(title,content);
        System.out.println(category);
    }
    @Test
    public void commenTag(){
        String text = Jsoup.parse("<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;" +
                "\">我最近有一个奇怪的发现哦，就是原创这个概念可能很多人是理解的，但是抄袭、借鉴、临摹三者之间的关系大家貌似就不太能区分了。</p>\n" +
                "<p><br></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\">所以今天，干脆就来展开讲解一下这几者之间的区别好了。</p>\n" +
                "<p><br></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\">我们先来看几个问题：</p>\n" +
                "<p><br></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\"><img style=\"\" data-ratio=\"1.1360708534621577\" data-type=\"jpeg\" data-w=\"1242\" data-s=\"300,640\" data-copyright=\"0\" src=\"/mpimg/TTTink-D/513B92BA78D802BAB6712FE8F653DC79.jpeg\"></p>\n" +
                "<p><br></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\">我想象了一下这位同学的描述，最终得出的结论是，如果你原封不动把这ABCD的内容临摹下来，并且一点都不做出改动，那依然将会被视作是抄袭，而不是借鉴；但如果你懂得适当的调整，最终得到的作品有了自己的特点，让人一眼都认不出和原来的作品有明显的相似之处，那就只能算是借鉴；而能力更强的人，他能把它借鉴到让人压根就看不出相似之处，并且那就可能被称为是原创了。</p>\n" +
                "<p><br></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\">再比如这个问题：</p>\n" +
                "<p><br></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\"><img style=\"\" data-ratio=\"0.38405797101449274\" data-type=\"jpeg\" data-w=\"1242\" data-s=\"300,640\" data-copyright=\"0\" src=\"/mpimg/TTTink-D/341D7890BD36593B9E7A81399D0C766C.jpeg\"></p>\n" +
                "<p><br></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\">毫无疑问，你这样做出来的作品，肯定是抄袭了，而且这种抄袭还可以有其他的方式表现，比如其他的都不变，只是把别人的文案换成自己的文案，再或者是其他的都不变，只是把别人的产品换成自己的产品等等，这些都可以说是抄袭。</p>\n" +
                "<p><br></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\">比如大家看看今年双11某个店铺的页面：</p>\n" +
                "<p style=\"text-align: center;line-height: 2em;margin-right: 8px;margin-left: 8px;\"><img style=\"\" data-ratio=\"0.58125\" data-type=\"jpeg\" data-w=\"1280\" data-s=\"300,640\" data-copyright=\"0\" src=\"/mpimg/TTTink-D/A05816F50F528A329BA6863F5179704F.jpeg\"></p>\n" +
                "<p style=\"text-align: left;\"><br></p>\n" +
                "<p style=\"text-align: left;line-height: 2em;margin-right: 8px;margin-left: 8px;\">再对比看下另一家店铺的页面：</p>\n" +
                "<p style=\"text-align: center;line-height: 2em;margin-right: 8px;margin-left: 8px;\"><img style=\"\" data-ratio=\"0.58203125\" data-type=\"jpeg\" data-w=\"1280\" data-s=\"300,640\" data-copyright=\"0\" src=\"/mpimg/TTTink-D/FD29FC536A707ECB7A8CCFA8BF129FC0.jpeg\"></p>\n" +
                "<p style=\"text-align: center;\"><br></p>\n" +
                "<p style=\"text-align: left;line-height: 2em;margin-right: 8px;margin-left: 8px;\">大家发现没有，这2者一眼就能看出完全就是一样的的，只是文案和产品替换了而已，所以其中一个肯定就是抄袭了，而且我们这里说是抄袭，而不是临摹，是因为这属于商业作品了，临摹只是针对于非商业作品的练习或者学习目的为准。</p>\n" +
                "<p style=\"text-align: left;\"><br></p>\n" +
                "<p style=\"text-align: left;line-height: 2em;margin-right: 8px;margin-left: 8px;\">嗯，据群里的小伙伴讲，是tutu抄袭了韩后。</p>\n" +
                "<p style=\"text-align: left;\"><br></p>\n" +
                "<p style=\"text-align: left;line-height: 2em;margin-right: 8px;margin-left: 8px;\">不过tutu后来又换了另外一个页面：</p>\n" +
                "<p style=\"text-align: center;line-height: 2em;margin-right: 8px;margin-left: 8px;\"><img style=\"\" data-ratio=\"0.546875\" data-type=\"jpeg\" data-w=\"1280\" data-s=\"300,640\" data-copyright=\"0\" src=\"/mpimg/TTTink-D/E54F9E0C98F28317F04AAB4D74BC4ADE.jpeg\"></p>\n" +
                "<p><br></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\">那么这个时候，你看，虽然依旧是用的礼盒和展台陈列的创意，依旧是大红+金色的颜色搭配，依旧是金属高光立体质感的效果处理，但是，这个Banner就与之前的视觉效果完全不一样了，成为了另外一个作品，只能说是借鉴，不能说是抄袭，原创倒是称不上了，因为这种效果处理和创意方向太常见了，已经不能算作是原创了。</p>\n" +
                "<p><br></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\">所以到这里，大家应该能够理解临摹、 抄袭、借鉴、原创之间的差别和联系了吧。</p>\n" +
                "<p><br></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\">通俗点说，我给大家总结一下：</p>\n" +
                "<p><br></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\"><strong>临摹是指，</strong>比如你看到一些很不错的作品，你很想做成它那样的效果，想拥有那样的创意，你想学习，那么你可以去临摹，也就是把别人的配色、布局、效果、文案、产品、细节、点缀元素、场景等等全部都复制一遍做出来，并且这只是你个人学习的用途，非商业用途，那么我们就称之为临摹。</p>\n" +
                "<p><br></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\">但这里要说明的是，临摹和模仿还是有一些差别的，模仿大多数是针对某一种行为，比如模仿某个人作图的动作、习惯、喜好、方式等等，而临摹则是针对的某一件作品。</p>\n" +
                "<p><br></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\"><strong>抄袭是指，</strong>比如你看到某一件作品，你觉得人家做的很不错，然后你又很懒或者你能力有限，所以你干脆直接把别人的作品拿来做项目商用了，最主要的场景或者主要的元素基本就没变，你只是更改了其中某一样或几样东西，比如文案啊，或者产品啊，或者配色等等，但是最终出来的效果和感觉跟原图放在一起，别人一眼就能认出他们几乎是同一个作品，那么我们就认定为这是抄袭了。</p>\n" +
                "<p><br></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\">抄袭和临摹最主要的差别就是，一个是商用的，一个是非商用的，而且抄袭行为是被业内认为不齿的，一旦被发现很可能就会影响自己的名声，而临摹则是大家都允许的一种学习方式。</p>\n" +
                "<p><br></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\"><strong>借鉴是指</strong>，比如你看到某一件作品，你觉得人家做得很不错，然后你会仔细去思考和分析，到底人家哪里做的不错，再结合自己要做的项目或者练习作品，你觉得人家哪些做得不错的地方你可以用在自己身上，融合进去，而不是照搬人家的成果，这样最终出来的作品，必定也是有了你自己的思想和灵魂在里面了，而且显得比较协调，看不出来和原作品有较明显的相似之处。</p>\n" +
                "<p><br></p>\n" +
                "<p style=\"text-align: center;line-height: 2em;margin-right: 8px;margin-left: 8px;\"><img style=\"\" data-ratio=\"0.75\" data-type=\"jpeg\" data-w=\"1280\" data-s=\"300,640\" data-copyright=\"0\" src=\"/mpimg/TTTink-D/11F8E59886F3AD62A7D79F902F85CDF5.jpeg\"></p>\n" +
                "<p style=\"text-align: center;line-height: 2em;margin-right: 8px;margin-left: 8px;\"><span style=\"color: rgb(136, 136, 136);font-size: 14px;\">（我今天的插花作品，喜欢吗</span><img style=\"display:inline-block;width:20px;vertical-align:text-bottom;\" data-ratio=\"1\" data-w=\"20\" src=\"/mpimg/TTTink-D/2C6C3BC2A57553CD2DFA86B07B01C81C.jpeg\"><span style=\"color: rgb(136, 136, 136);font-size: 14px;\">）</span></p>\n" +
                "<p style=\"text-align: center;\"><br></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\"><strong>至于原创，</strong>其实我们大多数人做的作品，目前也都只能算是这种借鉴得来的作品，大家也心知肚明，真正能算得上是原创的，还是比较少的。</p>\n" +
                "<p><br></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\">因为严格意义上的所谓原创，几乎是没有的，因为我们每天都会接触各种信息、看到各种各样的人和物，有时你都没在意，但这些却存在了你的潜意识里，某一天可能会突然蹦出来，变成了自己的灵感。</p>\n" +
                "<p><br></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\">除非你从出生之日起，你不看任何东西，不跟任何人交流，也不听任何人讲话，那么你所做出来的作品才可能算是真正意义上的完全原创，但，这大概也只有天才才做得到这样吧。</p>\n" +
                "<p><br></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\">所以，我们做设计去纠结原创不原创，其实没有意义，因为我们其实都只是在借鉴的基础上原创罢了。</p>\n" +
                "<p><br></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\">这也是为什么我们需要多看，多想，多交流，多思考，提倡跨界学习等等，因为借鉴并不可耻，因为懂得从他人身上、别的领域、大自然等等提取精华并运用，形成自己独特的作品本身就是一件了不起的能力和创新，我们也正是在这种互相学习借鉴的氛围下获得成长的。</p>\n" +
                "<p><br></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\">设计也好，科技也好，其他任何领域也好，都是这样的道理。</p>\n" +
                "<p><br></p>\n" +
                "<p style=\"text-align: center;line-height: 2em;margin-right: 8px;margin-left: 8px;\"><img style=\"\" data-ratio=\"1.3328125\" data-type=\"jpeg\" data-w=\"1280\" data-s=\"300,640\" data-copyright=\"0\" src=\"/mpimg/TTTink-D/A564B980AA240EF5B1799C4E895A2C2A.jpeg\"></p>\n" +
                "<p style=\"text-align: center;line-height: 2em;margin-right: 8px;margin-left: 8px;\"><span style=\"background-color: transparent;color: rgb(136, 136, 136);\">（我今天的插花作品，喜欢<img style=\"display:inline-block;width:20px;vertical-align:text-bottom;\" data-ratio=\"1\" data-w=\"20\" src=\"/mpimg/TTTink-D/2C6C3BC2A57553CD2DFA86B07B01C81C.jpeg\"></span><span style=\"background-color: transparent;color: rgb(136, 136, 136);\">）</span></p>\n" +
                "<p style=\"text-align: center;line-height: 2em;margin-right: 8px;margin-left: 8px;\"><span style=\"background-color: transparent;color: rgb(136, 136, 136);\"><br></span></p>\n" +
                "<p><br></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\"><strong>写在最后</strong></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\">很多人刚入行做设计，可能会比较纠结这些概念方面的问题，也比较迷惑到底什么行为是可以做的，什么行为是不可以做的，也不清楚自己做了到底会有什么用，那么你只需要记住下面这条建议就好了：</p>\n" +
                "<p><br></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\"><strong>1、</strong><span style=\"display: inline !important;float: none;background-color: transparent;color: rgb(0, 0, 0);\" sans=\"sans\" helvetica=\"helvetica\" px=\"px\" none=\"none\" left=\"left\" normal=\"normal\" yahei=\"yahei\" gb=\"gb\" neue=\"neue\">如果你确认了自己并不是天才，那就不要一开始入行就老想着原创了，因为你可能还hold不住，还是好好打好基础，从临摹做起吧；</span></p>\n" +
                "<p><br></p>\n" +
                "<p style=\"color: rgb(0, 0, 0);line-height: 2em;clear: both;margin-right: 8px;margin-left: 8px;background-color: transparent;\"><strong>2、</strong>可以临摹学习，但是不要一直只是停留在模仿的阶段，因为你的思维可能会僵化，你会失去思考的能力，到那一天，你可能就被人工智能淘汰了；</p>\n" +
                "<p><br></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\"><strong>3、</strong>可以临摹学习，也可以借鉴别人优秀的点商用，但不要抄袭；<br></p>\n" +
                "<p><br></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\"><strong>4、</strong>量变决定质变，光靠项目作品磨练自己是不够的，<span style=\"display: inline !important;float: none;background-color: transparent;color: rgb(0, 0, 0);\" sans=\"sans\" helvetica=\"helvetica\" px=\"px\" none=\"none\" left=\"left\" normal=\"normal\" yahei=\"yahei\" gb=\"gb\" neue=\"neue\">没有人是可以不做练习不下苦功夫就可以成为很厉害的设计师的；</span></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\"><span style=\"display: inline !important;float: none;background-color: transparent;color: rgb(0, 0, 0);\" sans=\"sans\" helvetica=\"helvetica\" px=\"px\" none=\"none\" left=\"left\" normal=\"normal\" yahei=\"yahei\" gb=\"gb\" neue=\"neue\"><br></span></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\">如果你不知道如何做练习，也许这几篇文章可以帮到你：</p>\n" +
                "<p><br></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\"><a href=\"http://mp.weixin.qq.com/s?__biz=MzAwMDY3ODEzMw==&amp;mid=2651772946&amp;idx=1&amp;sn=18331bc94f2c87680f73293beecb2436&amp;scene=21#wechat_redirect\" target=\"_blank\"><strong>看了那么多大神的作品为什么依旧做不出好的作品呢？</strong></a><br></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\"><a href=\"http://mp.weixin.qq.com/s?__biz=MzAwMDY3ODEzMw==&amp;mid=2651777052&amp;idx=3&amp;sn=257f0c9ec5934b950758e3a02fd288cc&amp;chksm=811f0986b66880900ae0a0ca436f1664656f096af07bd7055dd73c12747d9fc6146e7cd2497e&amp;scene=21#wechat_redirect\" target=\"_blank\"><strong>别骗自己了，其实你一直都在做假练习！～</strong></a></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\"><a style=\"background-color: transparent;color: rgb(96, 127, 166);\" href=\"http://mp.weixin.qq.com/s?__biz=MzAwMDY3ODEzMw==&amp;mid=2651772946&amp;idx=1&amp;sn=18331bc94f2c87680f73293beecb2436&amp;scene=21#wechat_redirect\" target=\"_blank\"></a><a style=\"background-color: transparent;color: rgb(96, 127, 166);\" href=\"http://mp.weixin.qq.com/s?__biz=MzAwMDY3ODEzMw==&amp;mid=2651778366&amp;idx=2&amp;sn=a97e95851b4b553b8873e5b91350ff8d&amp;chksm=811f0ca4b66885b218d208315dddc69196cbd1a291db75c47502220c95c40de5107b5c4c2610&amp;scene=21#wechat_redirect\" target=\"_blank\"><strong>《做设计的面条》第17期设计练习之详情页设计点评</strong></a></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\"><a href=\"http://mp.weixin.qq.com/s?__biz=MzAwMDY3ODEzMw==&amp;mid=2651775870&amp;idx=3&amp;sn=c5bf3fa627e789cd3a55be058924051d&amp;chksm=811f02e4b6688bf211677ea13a18536454b022796a63a5ed0a5fdadd28f82c7065fc7e866d28&amp;scene=21#wechat_redirect\" target=\"_blank\"><strong>［练习点评］每期15个点评分享，这里有你花钱都买不到的干货！～</strong></a></p>\n" +
                "<p><br></p>\n" +
                "<p style=\"text-align: center;line-height: 2em;margin-right: 8px;margin-left: 8px;\"><img style=\"\" data-ratio=\"1.3328125\" data-type=\"jpeg\" data-w=\"1280\" data-s=\"300,640\" data-copyright=\"0\" src=\"/mpimg/TTTink-D/12BD4E0250158C639882753A12F70CB9.jpeg\"></p>\n" +
                "<p style=\"text-align: center;line-height: 2em;margin-right: 8px;margin-left: 8px;\"><span style=\"background-color: transparent;color: rgb(136, 136, 136);\">（我今天的插花作品，喜欢<img style=\"display:inline-block;width:20px;vertical-align:text-bottom;\" data-ratio=\"1\" data-w=\"20\" src=\"/mpimg/TTTink-D/2C6C3BC2A57553CD2DFA86B07B01C81C.jpeg\"></span><span style=\"background-color: transparent;color: rgb(136, 136, 136);\">）</span></p>\n" +
                "<p style=\"text-align: center;line-height: 2em;margin-right: 8px;margin-left: 8px;\"><span style=\"background-color: transparent;color: rgb(136, 136, 136);\"><br></span></p>\n" +
                "<p style=\"text-align: left;line-height: 2em;margin-right: 8px;margin-left: 8px;\"><span style=\"color: rgb(0, 0, 0);background-color: transparent;\">你们发现没有，今天的文章出奇的短。</span><span style=\"color: rgb(0, 0, 0);background-color: transparent;\"><br></span></p>\n" +
                "<p style=\"text-align: left;line-height: 2em;margin-right: 8px;margin-left: 8px;\"><span style=\"color: rgb(0, 0, 0);background-color: transparent;\">因为！我在外地！over！~<img style=\"display:inline-block;width:20px;vertical-align:text-bottom;\" data-ratio=\"1\" data-w=\"20\" src=\"/mpimg/TTTink-D/B14CE9464D2C7742BDB65121E4202564.jpeg\"></span></p>\n" +
                "<p style=\"text-align: left;line-height: 2em;margin-right: 8px;margin-left: 8px;\"><span style=\"color: rgb(0, 0, 0);background-color: transparent;\"><br></span></p>\n" +
                "<p style=\"text-align: center;\"><img style=\"\" data-ratio=\"0.553030303030303\" data-type=\"jpeg\" data-w=\"264\" data-s=\"300,640\" data-copyright=\"0\" src=\"/mpimg/TTTink-D/71BA31383EBA73A3B4ECCF450ED940DF.jpeg\"></p>\n" +
                "<p style=\"text-align: left;line-height: 2em;margin-right: 8px;margin-left: 8px;\"><br></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\"><span style=\"display: inline !important;float: none;background-color: transparent;color: rgb(0, 0, 0);\" sans=\"sans\" helvetica=\"helvetica\" px=\"px\" none=\"none\" left=\"left\" normal=\"normal\" yahei=\"yahei\" gb=\"gb\" neue=\"neue\" break-word=\"break-word\">好了，这里是</span><strong style=\"background-color: transparent;box-sizing: border-box;color: rgb(0, 0, 0);\">《做设计的面条》</strong><span style=\"display: inline !important;float: none;background-color: transparent;color: rgb(0, 0, 0);\" sans=\"sans\" helvetica=\"helvetica\" px=\"px\" none=\"none\" left=\"left\" normal=\"normal\" yahei=\"yahei\" gb=\"gb\" neue=\"neue\" break-word=\"break-word\">，一个可以帮助你成长的电商设计公众号，喜欢我的文章就</span><span style=\"background-color: rgb(255, 41, 65);color: rgb(255, 255, 255);\"><strong style=\"box-sizing: border-box;margin-bottom: 0px;margin-left: 0px;margin-right: 0px;margin-top: 0px;max-width: 100%;padding-bottom: 0px;padding-left: 0px;padding-right: 0px;padding-top: 0px;word-wrap: break-word;\">把我设置为星标</strong></span><span style=\"display: inline !important;float: none;background-color: transparent;color: rgb(0, 0, 0);\" sans=\"sans\" helvetica=\"helvetica\" px=\"px\" none=\"none\" left=\"left\" normal=\"normal\" yahei=\"yahei\" gb=\"gb\" neue=\"neue\" break-word=\"break-word\">吧，我们下期再见！～</span></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\"><span style=\"display: inline !important;float: none;background-color: transparent;color: rgb(0, 0, 0);\" sans=\"sans\" helvetica=\"helvetica\" px=\"px\" none=\"none\" left=\"left\" normal=\"normal\" yahei=\"yahei\" gb=\"gb\" neue=\"neue\" break-word=\"break-word\"><br></span></p>\n" +
                "<p style=\"line-height: 2em;margin-right: 8px;margin-left: 8px;\"><img style=\"background-color: transparent;box-sizing: border-box;color: rgb(0, 0, 0);\" data-ratio=\"0.46796875\" data-type=\"jpeg\" data-w=\"1280\" data-s=\"300,640\" data-copyright=\"0\" src=\"/mpimg/TTTink-D/DCDB2CFAA8E91D09B5A52DFFF282895A.jpeg\"></p>\n" +
                "<p style=\"text-align: center;line-height: 2em;margin-right: 8px;margin-left: 8px;\"><span style=\"font-size: 14px;\">------这里是<span style=\"color: rgb(255, 41, 65);\">做设计的面条</span>的第<span style=\"color: rgb(255, 41, 65);\">361</span>篇原创文章------</span></p>\n" +
                "<p style=\"text-align: center;line-height: 2em;margin-right: 8px;margin-left: 8px;\"><span style=\"text-align: center;color: rgb(0, 0, 0);text-transform: none;text-indent: 0px;letter-spacing: normal;font-size: 14px;\">转载请注明公众号名称和ID</span></p>\n" +
                "<p><br></p>").text();
        String title = "关于设计，我们到底该如何定义抄袭、借鉴、临摹和原创？";
        String str = nlpService.getSummary(title,text);
        System.out.println(str);
    }
}
<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

// 数据部分
const images = ref([
  { src: '/src/assets/首页image/风景1.jpg', alt: '疗愈图片1', caption: '宁静的自然风光' },
  { src: '/src/assets/首页image/风景2.jpg', alt: '疗愈图片2', caption: '柔和的日落' },
  { src: '/src/assets/首页image/风景3.jpg', alt: '疗愈图片3', caption: '平静的湖面' },
  { src: '/src/assets/首页image/风景4.jpg', alt: '疗愈图片4', caption: '温暖的阳光' },
  { src: '/src/assets/首页image/风景5.jpg', alt: '疗愈图片5', caption: '清新的森林' },
  { src: '/src/assets/首页image/风景6.jpg', alt: '疗愈图片6', caption: '静谧的夜晚' }
]);

const features = ref([
  { 
    title: '🎵 精准音乐搜索', 
    description: '通过平台的精准搜索功能，用户可以根据个人需求快速找到最适合自己的音乐，满足不同的情感调节需求。例如，想要缓解焦虑时，平台会推荐柔和的钢琴曲或自然声音；提升情绪时，可以选择动感的音乐或积极向上的旋律；进入冥想状态时，平台会精准推送冥想音乐、白噪音或深度放松的声音环境。' 
  },
  { 
    title: '🤖 智能音乐推荐', 
    description: '平台通过智能推荐系统，结合用户的听歌历史、情感状态、和心理需求，自动推荐个性化的音乐疗愈方案。基于AI分析，系统会在用户的情感变化时动态调整推荐内容，帮助用户发现符合当前需求的优质音乐，从而提升音乐疗愈的效果，形成一个持续优化的治疗体验。' 
  },
  { 
    title: '🧠 AI情绪调节', 
    description: '利用最先进的人工智能技术，平台能够实时分析用户的情感波动，并根据情绪变化动态调整推荐的音乐内容。例如，在用户出现焦虑、压力增大的时候，平台会播放具有平和感的音轨，帮助缓解压力；如果用户需要帮助入睡，系统则会推送舒缓的睡眠音乐。通过这种智能调节，用户可以更好地掌控自己的情感状态，达到最佳的心理疗愈效果。' 
  },
  { 
    title: '🌿 沉浸式疗愈场景', 
    description: '平台提供多种沉浸式音景选择，帮助用户创建理想的疗愈环境。除了精选的舒缓音乐，用户还可以选择自然声音，如雨声、海浪、风声等，甚至是森林中鸟鸣的声音，来放松身心。这些音景可以组合使用，为用户带来全方位的疗愈体验，帮助恢复身心的平衡与健康。' 
  },
  { 
    title: '🌍 社交互动功能', 
    description: '平台鼓励用户在使用过程中分享自己的疗愈体验，创建和加入健康的音乐社群，互相支持与鼓励。通过与他人分享自己的心情、疗愈心得和进步，用户不仅能够获得更多的音乐推荐，还能够形成一个积极向上的社交氛围。这样，大家可以一起探索心理健康的道路，互相鼓励，共同迈向更快乐的生活。' 
  },
  { 
    title: '🗂️ 个人管理中心', 
    description: '提供全面的个人管理功能，帮助用户查看和管理自己的音乐收藏、情感记录与治疗进程，并根据个人需求定制疗愈方案。' 
  }
]);

const scientificLinks = ref([
  { href: 'https://shh.bjmu.edu.cn/yxrwjt/LEJZ224368.htm', text: '《音乐治疗的疗效及潜在机制》' },
  { href: 'https://m.thepaper.cn/newsDetail_forward_30301019', text: '《旋律中的疗愈：走进音乐治疗》' },
  { href: 'https://www.tsinghua.edu.cn/info/1182/116977.htm', text: '《当古琴拨动 "神经"，这里有一场音乐 "疗愈"》' },
  { href: 'https://shh.bjmu.edu.cn/yxrwjt/7244db9a59384903a3202f37fbe3bbc3.htm', text: '《针对物质成瘾者的音乐治疗的研究及应用进展》' },
  { href: 'https://shh.bjmu.edu.cn/yxrwjt/326c79a0e6764e86938a79064b393bc2.htm', text: '《身心疗愈的音乐之旅 —— 音乐治疗的应用与发展》' },
  { href: 'https://m.thepaper.cn/kuaibao_detail.jsp?contid=28769257&from=kuaibao', text: '《情绪解药：新研究证明多听音乐有助于情绪调节》' },
  { href: 'http://m.toutiao.com/group/7480800064517030454/?upstream_biz=doubao', text: '《音乐如何疗愈？演奏家马晓辉：二胡能与心灵对话》' },
  { href: 'http://mp.weixin.qq.com/s?__biz=MzU5MjMyMzgxOA==&mid=2247484020&idx=1&sn=8ca1125265ee6f4851d94ae7d0f8143b&scene=0', text: '《科普 | 古代就有音乐疗愈？探寻音乐疗愈古代历史》' },
  { href: 'https://www.shanghai.gov.cn/nw4411/20241128/b1bb4b85195149968b22613541fa2c4c.html', text: '《人工智能音乐疗愈重点实验室成立 为患者定制 "声音疗愈胶囊"》' },
  { href: 'https://m.thepaper.cn/newsDetail_forward_28254994', text: '《数智赋能大健康时代的深入实践 —— 音乐人工智能与声音疗愈的交叉融合》' }
]);

const newsItems = ref([
  { 
    href: 'https://www.sohu.com/a/883162420_121956424', 
    imgSrc: 'https://picsum.photos/320/200', 
    alt: '疗愈资讯1', 
    description: '阿尔法脑波音乐每分钟 60 拍、频率为 8 - 12Hz，研究显示其能让大脑产生 "频率跟随效应"，促进大脑的 α 波与 θ 波同步，增加前额叶皮层的血流量，进而增强认知功能，减轻焦虑。' 
  },
  { 
    href: 'https://win.bupt.edu.cn/program.do?id=7512', 
    imgSrc: 'https://picsum.photos/320/201', 
    alt: '疗愈资讯2', 
    description: '"婴儿呼吸法对失眠群体的音乐疗愈研究" 项目旨在研究婴儿呼吸法和声乐技巧结合对于失眠群体的积极疗效，并探索将理论产品化的可能性。' 
  },
  { 
    href: 'https://www.hainmc.edu.cn/psy/info/1111/2291.htm', 
    imgSrc: 'https://picsum.photos/320/202', 
    alt: '疗愈资讯3', 
    description: '中央音乐学院刘明明副教授在讲座中，从神经科学视角，结合神经音乐治疗学派（NMT）前沿成果与案例，通过节奏夹带、联觉效应等，阐释音乐发挥疗效的依据，解析音乐与记忆、情绪、人际互动的关系。' 
  },
  { 
    href: 'https://m.sohu.com/a/825819031_121956422/', 
    imgSrc: 'https://picsum.photos/320/203', 
    alt: '疗愈资讯4', 
    description: '上海四院和上海大学音乐学院合作建立了音乐疗愈实践基地。在这里，会按照医疗科室的特点以及患者的实际状况，来制定个性化的音乐疗愈方案。在疗愈过程中，医生和音乐治疗师会密切配合，根据患者的反应及时调整音乐 TÜT 和演奏风格。' 
  },
  { 
    href: 'https://www.tsinghua.edu.cn/info/1182/116977.htm', 
    imgSrc: 'https://picsum.photos/320/205', 
    alt: '疗愈资讯5', 
    description: '清华大学脑与智能实验室团队将实验场景延伸至真实的音乐演出现场，捕捉音乐家与听众的神经活动，还将中国传统音乐纳入核心研究范畴，提出 "个性化音乐医疗" 概念，计划应用于全生命周期健康关怀。' 
  }
]);

const courses = ref([
  { title: '课程一：放松冥想音乐疗愈', description: '通过舒缓的音乐引导你进入深度冥想状态，放松身心。' },
  { title: '课程二：睡眠助眠音乐疗愈', description: '精选助眠音乐，帮助你改善睡眠质量，拥有好睡眠。' },
  { title: '课程三：情绪调节音乐疗愈', description: '运用特定音乐调节情绪，让你保持积极心态。' },
  { title: '课程四：专注力提升音乐疗愈', description: '借助音乐提升专注力，提高学习和工作效率。' }
]);

const currentTime = ref('');
const searchTerm = ref('');
const slideIndex = ref(0);
const isCourseModalVisible = ref(false);
const selectedCourse = ref({});
const autoSlideInterval = ref(null);

// 方法部分
function navigateTo(path) {
  router.push(path);
}

function showCurrentTime() {
  const now = new Date();
  const year = now.getFullYear();
  const month = String(now.getMonth() + 1).padStart(2, '0');
  const day = String(now.getDate()).padStart(2, '0');
  const hours = String(now.getHours()).padStart(2, '0');
  const minutes = String(now.getMinutes()).padStart(2, '0');
  const seconds = String(now.getSeconds()).padStart(2, '0');
  currentTime.value = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}

function showSlide(index) {
  if (index < 0) {
    slideIndex.value = images.value.length - 1;
  } else if (index >= images.value.length) {
    slideIndex.value = 0;
  } else {
    slideIndex.value = index;
  }
}

function prevSlide() {
  showSlide(slideIndex.value - 1);
  resetAutoSlide();
}

function nextSlide() {
  showSlide(slideIndex.value + 1);
  resetAutoSlide();
}

function goToSlide(index) {
  showSlide(index);
  resetAutoSlide();
}

function startAutoSlide() {
  autoSlideInterval.value = setInterval(nextSlide, 5000);
}

function resetAutoSlide() {
  clearInterval(autoSlideInterval.value);
  startAutoSlide();
}

function openCourseModal(course) {
  selectedCourse.value = course;
  isCourseModalVisible.value = true;
}

function closeCourseModal() {
  isCourseModalVisible.value = false;
}

function search() {
  alert(`你搜索的内容是: ${searchTerm.value}`);
}

// 生命周期钩子
onMounted(() => {
  // 初始化轮播
  showSlide(slideIndex.value);
  startAutoSlide();
  
  // 启动时间显示和定时更新
  showCurrentTime();
  const timeInterval = setInterval(showCurrentTime, 1000);
  
  // 保存interval ID以便在组件卸载时清除
  autoSlideInterval.value = timeInterval;
});

onUnmounted(() => {
  // 清除所有定时器
  if (autoSlideInterval.value) {
    clearInterval(autoSlideInterval.value);
  }
});
</script>

<template>
  <div>
    <!-- 顶部区域 -->
    <header>
      <div id="logo">HEARTCURE</div>
      <div id="search-login-register">
        <div id="search-container">
          <input type="text" id="search-input" placeholder="搜索内容" v-model="searchTerm">
          <button id="search-button" @click="search">搜索</button>
        </div>
        <div id="login-register">
          <button id="login" @click="navigateTo('/login')">
            <i class="fa-solid fa-right-to-bracket"></i> 登录注册
          </button>
        </div>
      </div>
    </header>

    <!-- 导航栏 -->
    <nav>
      <span id="current-time">{{ currentTime }}</span>
      <ul>
        <li>
          <a @click.prevent="navigateTo('/healing')">
            <i class="fa-solid fa-music"></i> 疗愈
          </a>
          <div class="nav-float">
            <div @click="navigateTo('/music-search')">音乐搜索</div>
            <div @click="navigateTo('/healing-music-library')">疗愈音乐库</div>
            <div @click="navigateTo('/ai-healing')">AI助愈</div>
          </div>
        </li>
        <li>
          <a @click.prevent="navigateTo('/my-groups')">
            <i class="fa-solid fa-users"></i> 社交
          </a>
          <div class="nav-float">
            <div @click="navigateTo('/recommended')">推荐内容</div>
            <div @click="navigateTo('/write-post')">写帖子</div>
            <div @click="navigateTo('/my-groups')">分组疗愈</div>
            <div @click="navigateTo('/search-posts')">查找帖子</div>
          </div>
        </li>
        <li>
          <a @click.prevent="navigateTo('/personal-page')">
            <i class="fa-solid fa-user"></i> 个人
          </a>
          <div class="nav-float">
            <div @click="navigateTo('/mood-calendar')">心情日历</div>
            <div @click="navigateTo('/psychological-diary')">心理日记</div>
            <div @click="navigateTo('/psych-test')">心理测试</div>
            <div @click="navigateTo('/my-playlists')">我的歌单</div>
            <div @click="navigateTo('/my-favorites')">我的收藏</div>
            <div @click="navigateTo('/settings')">设置</div>
          </div>
        </li>
      </ul>
    </nav>

    <!-- 疗愈图片 -->
    <div id="slider-container">
      <div id="slider">
        <div 
          v-for="(image, index) in images" 
          :key="index" 
          class="slide" 
          :class="{ 'active': index === slideIndex }"
        >
          <img :src="image.src" :alt="image.alt">
          <div class="slide-caption">{{ image.caption }}</div>
        </div>
      </div>
      <button class="slider-arrow" id="prev-arrow" @click="prevSlide"><i class="fa-solid fa-chevron-left"></i></button>
      <button class="slider-arrow" id="next-arrow" @click="nextSlide"><i class="fa-solid fa-chevron-right"></i></button>
      <div id="slider-dots">
        <span 
          v-for="(_, index) in images" 
          :key="index"
          class="dot"
          :class="{ 'active': index === slideIndex }"
          @click="goToSlide(index)"
        ></span>
      </div>
    </div>

    <!-- 网站介绍 -->
    <section id="website-introduction">
      <h1>网站介绍</h1>
      <p>本音乐疗愈平台的构思源于对人们心理健康的关注。在现代快节奏的生活中，人们面临着各种各样的压力和挑战，心理健康问题日益凸显。音乐作为一种强大的艺术形式，具有独特的疗愈力量，能够帮助人们缓解压力、调节情绪、放松身心。</p>
      <h2>平台的核心功能：</h2>
      <ul>
        <li v-for="(feature, index) in features" :key="index">
          <h3>{{ feature.title }}</h3>
          <p>{{ feature.description }}</p>
        </li>
      </ul>
    </section>

    <!-- 科学支持板块 -->
    <section id="scientific-support">
      <div class="divider">
        <span class="divider-title">科学支持</span>
      </div>
      <div class="scientific-container">
        <div class="video-container">
          <video src="/src/assets/首页image/科学支持.mp4" controls></video>
        </div>
        <div class="text-links-container">
          <a v-for="(link, index) in scientificLinks" :key="index" :href="link.href">{{ link.text }}</a>
        </div>
      </div>
    </section>

    <!-- 疗愈资讯 -->
    <section id="healing-news">
      <div class="divider">
        <span class="divider-title">疗愈资讯</span>
      </div>
      <div class="news-container">
        <div 
          v-for="(news, index) in newsItems" 
          :key="index" 
          class="news-item" 
          @click="() => window.open(news.href, '_blank')"
        >
          <img :src="news.imgSrc" :alt="news.alt">
          <p>{{ news.description }}</p>
        </div>
      </div>
    </section>

    <!-- 课程推荐 -->
    <section id="course-recommendation">
      <div class="divider">
        <span class="divider-title">疗愈课程推荐</span>
      </div>
      <div class="course-container">
        <div 
          v-for="(course, index) in courses" 
          :key="index" 
          class="course-item"
          @click="openCourseModal(course)"
        >
          <h3>{{ course.title }}</h3>
          <p>{{ course.description }}</p>
        </div>
      </div>
    </section>

    <!-- 课程模态框 -->
    <div id="course-modal" class="modal" v-show="isCourseModalVisible">
      <div class="modal-content">
        <span class="close" @click="closeCourseModal">×</span>
        <h2 id="course-modal-title">{{ selectedCourse.title }}</h2>
        <p id="course-modal-description">{{ selectedCourse.description }}</p>
      </div>
    </div>

    <!-- 右侧固定栏 -->
    <div id="fixed-sidebar">
      <ul>
        <li><a href="#website-introduction">网站介绍</a></li>
        <li><a href="#scientific-support">科学支持</a></li>
        <li><a href="#healing-news">疗愈资讯</a></li>
        <li><a href="#course-recommendation">课程推荐</a></li>
      </ul>
    </div>

   <!-- 页脚部分 -->
   <footer class="bg-gradient-to-b from-[#e9f6ff] to-[#cbf2fe] text-[#333] py-12 px-6 md:px-12 shadow-lg">
        <div class="max-w-7xl mx-auto grid grid-cols-1 md:grid-cols-3 gap-8">
            <!-- 关于我们 -->
            <div>
                <h3 class="text-2xl font-bold text-[#6bbaf3] mb-4">关于 HEARTCURE</h3>
                <p class="text-base text-[#4b5e77] leading-relaxed">
                    HEARTCURE 是一个致力于通过音乐疗愈帮助用户缓解压力、调节情绪的平台。我们结合 AI 技术和科学支持，为您提供个性化的疗愈体验。
                </p>
            </div>
            <!-- 快速链接 -->
            <div>
                <h3 class="text-2xl font-bold text-[#6bbaf3] mb-4">快速链接</h3>
                <ul class="space-y-2">
                    <li><a href="#website-introduction"
                            class="text-base text-[#4b5e77] hover:text-[#3b82f6] transition-colors duration-300">网站介绍</a>
                    </li>
                    <li><a href="#scientific-support"
                            class="text-base text-[#4b5e77] hover:text-[#3b82f6] transition-colors duration-300">科学支持</a>
                    </li>
                    <li><a href="#healing-news"
                            class="text-base text-[#4b5e77] hover:text-[#3b82f6] transition-colors duration-300">疗愈资讯</a>
                    </li>
                    <li><a href="#course-recommendation"
                            class="text-base text-[#4b5e77] hover:text-[#3b82f6] transition-colors duration-300">课程推荐</a>
                    </li>
                    <li><a @click="navigateTo('/healing')"
                            class="text-base text-[#4b5e77] hover:text-[#3b82f6] transition-colors duration-300 cursor-pointer">疗愈音乐</a>
                    </li>
                </ul>
            </div>
            <!-- 联系我们 -->
            <div>
                <h3 class="text-2xl font-bold text-[#6bbaf3] mb-4">联系我们</h3>
                <ul class="space-y-2">
                    <li class="flex items-center gap-2">
                        <i class="fa-solid fa-envelope text-[#3b82f6]"></i>
                        <a href="mailto:support@heartcure.com"
                            class="text-base text-[#4b5e77] hover:text-[#3b82f6] transition-colors duration-300">support@heartcure.com</a>
                    </li>
                    <li class="flex items-center gap-2">
                        <i class="fa-solid fa-phone text-[#3b82f6]"></i>
                        <span class="text-base text-[#4b5e77]">+86 123-456-7890</span>
                    </li>
                    <li class="flex items-center gap-2">
                        <i class="fa-solid fa-map-marker-alt text-[#3b82f6]"></i>
                        <span class="text-base text-[#4b5e77]">中国上海</span>
                    </li>
                </ul>
                <!-- 社交媒体图标 -->
                <div class="mt-4 flex gap-4">
                    <a href="#" class="text-[#4b5e77] hover:text-[#3b82f6] transition-colors duration-300">
                        <i class="fa-brands fa-weixin text-2xl"></i>
                    </a>
                    <a href="#" class="text-[#4b5e77] hover:text-[#3b82f6] transition-colors duration-300">
                        <i class="fa-brands fa-weibo text-2xl"></i>
                    </a>
                    <a href="#" class="text-[#4b5e77] hover:text-[#3b82f6] transition-colors duration-300">
                        <i class="fa-brands fa-qq text-2xl"></i>
                    </a>
                </div>
            </div>
        </div>
        <!-- 版权信息 -->
        <div class="mt-8 text-center border-t border-[#86b6cd] pt-6">
            <p class="text-sm text-[#4b5e77]">&copy; 2025 HEARTCURE. 保留所有权利。</p>
        </div>
    </footer>
  </div>
</template>

<style scoped>
/* 通用样式 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

/* 页面加载动画 */
section {
  opacity: 0;
  transform: translateY(20px);
  animation: fadeInSection 0.8s ease forwards;
}

@keyframes fadeInSection {
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 顶部样式 */
header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 40px;
  background: linear-gradient(to right, #f2f6f8, #d0ebf8);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
}

header:hover {
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
}

#logo {
  font-size: 32px;
  font-weight: 800;
  color: #6bbaf3;
  letter-spacing: -1px;
  transition: color 0.3s ease;
}

#logo:hover {
  color: #4c8cd6;
}

#search-login-register {
  display: flex;
  align-items: center;
  gap: 60px;
}

#search-container {
  display: flex;
  background-color: #f3f6f9;
  border-radius: 8px;
  padding: 6px;
  box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

#search-container:hover {
  box-shadow: inset 0 3px 6px rgba(0, 0, 0, 0.15);
}

#search-input {
  padding: 8px 12px;
  border: none;
  background-color: transparent;
  width: 300px;
  font-size: 16px;
  outline: none;
  transition: width 0.3s ease;
}

#search-input:focus {
  width: 350px;
}

#search-input::placeholder {
  color: #999;
}

#search-button {
  padding: 8px 15px;
  border: none;
  border-radius: 6px;
  background: linear-gradient(135deg, #74b1f2 0%, #4c8fd6 100%);
  color: white;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  position: relative;
  overflow: hidden;
}

#search-button::before {
  content: "";
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: rgba(255, 255, 255, 0.2);
  transition: left 0.3s ease;
  transform: skewX(-20deg);
}

#search-button:hover::before {
  left: 100%;
}

#search-button:hover {
  background: linear-gradient(135deg, #4c8fd6 0%, #8EC5FC 100%);
  transform: translateY(-2px);
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
}

#search-button:active {
  animation: bounce 0.2s ease;
}

@keyframes bounce {
  0% {
    transform: scale(1);
  }

  50% {
    transform: scale(0.95);
  }

  100% {
    transform: scale(1);
  }
}

#login-register {
  display: flex;
  gap: 30px;
}

#login {
  padding: 12px 24px;
  border: none;
  border-radius: 30px;
  background: linear-gradient(135deg, #b7dcf1 0%, #6facdf 50%, #4298ef 100%);
  color: white;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 16px;
  font-weight: 600;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
  position: relative;
  overflow: hidden;
}

#login::before {
  content: "";
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: rgba(255, 255, 255, 0.2);
  transition: left 0.3s ease;
  transform: skewX(-20deg);
}

#login:hover::before {
  left: 100%;
}

#login:hover {
  background: linear-gradient(135deg, #5c8fd1 0%, #4c8fd6 50%, #73a6ff 100%);
  transform: translateY(-3px);
  box-shadow: 0 6px 10px rgba(0, 0, 0, 0.3);
}

#login:active {
  animation: bounce 0.2s ease;
}

/* 导航栏样式 */
nav {
  background: linear-gradient(to bottom, #e9f6ff, #cbf2fe);
  padding: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 500px;
  position: relative;
  transition: all 0.3s ease;
}

nav:hover {
  background: linear-gradient(to bottom, #e2f6fa, #c0e7ff);
}

#current-time {
  font-size: 16px;
  color: #1a2a44;
  position: absolute;
  left: 40px;
  top: 50%;
  transform: translateY(-50%);
  background: rgba(255, 255, 255, 0.9);
  padding: 8px 16px;
  border-radius: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  font-weight: 500;
  letter-spacing: 0.5px;
  transition: all 0.3s ease;
  display: inline-flex;
  align-items: center;
  gap: 8px;
}

#current-time::before {
  content: "\f017"; /* Font Awesome clock icon */
  font-family: "Font Awesome 6 Free";
  font-weight: 900;
  color: #3b82f6;
  font-size: 14px;
}

#current-time:hover {
  background: #ffffff;
  color: #3b82f6;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
  transform: translateY(-50%) scale(1.05);
}

nav ul {
  list-style-type: none;
  display: flex;
  gap: 200px;
}

nav ul li {
  position: relative;
}

nav ul li a {
  color: #333;
  text-decoration: none;
  font-size: 18px;
  transition: all 0.3s ease;
  padding: 10px;
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
}

nav ul li a:hover {
  color: #a4d5f4;
  background-color: rgba(0, 123, 255, 0.1);
  border-radius: 5px;
  transform: scale(1.1) rotate(2deg);
}

.nav-float {
  display: none;
  position: absolute;
  top: 100%;
  left: 0;
  background-color: white;
  border: none;
  border-radius: 8px;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
  padding: 15px;
  z-index: 1;
  min-width: 180px;
  animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }

  to {
    opacity: 1;
    transform: translateY(0);
  }
}

nav ul li:hover .nav-float {
  display: block;
}

.nav-float div {
  padding: 10px 15px;
  cursor: pointer;
  transition: all 0.3s ease;
  border-radius: 5px;
  font-size: 16px;
}

.nav-float div:hover {
  color: #94c1f1;
  background-color: #e9f6ff;
  transform: translateX(5px);
}

/* 图片展示样式 */
#slider-container {
  position: relative;
  width: 100%;
  height: 600px;
  margin-bottom: 40px;
  overflow: hidden;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

#slider {
  position: relative;
  width: 100%;
  height: 100%;
}

.slide {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: 0;
  transition: opacity 0.5s ease;
}

.slide.active {
  opacity: 1;
}

.slide img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  filter: brightness(0.9);
  transition: filter 0.3s ease;
}

.slide:hover img {
  filter: brightness(1);
}

.slide-caption {
  position: absolute;
  bottom: 20px;
  left: 20px;
  background: rgba(0, 0, 0, 0.5);
  color: white;
  padding: 10px 20px;
  border-radius: 5px;
  opacity: 0;
  transform: translateY(20px);
  transition: all 0.3s ease;
}

.slide.active .slide-caption {
  opacity: 1;
  transform: translateY(0);
}

.slider-arrow {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background-color: rgba(0, 0, 0, 0.3);
  color: white;
  border: none;
  padding: 15px;
  cursor: pointer;
  z-index: 10;
  border-radius: 50%;
  font-size: 20px;
  transition: background-color 0.3s ease;
}

.slider-arrow:hover {
  background-color: rgba(0, 0, 0, 0.5);
  transform: translateY(-50%) scale(1.1);
}

#prev-arrow {
  left: 20px;
}

#next-arrow {
  right: 20px;
}

#slider-dots {
  position: absolute;
  bottom: 20px;
  width: 100%;
  text-align: center;
  z-index: 10;
}

.dot {
  display: inline-block;
  width: 12px;
  height: 12px;
  background-color: rgba(255, 255, 255, 0.5);
  border-radius: 50%;
  margin: 0 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.dot.active {
  background-color: #fff;
}

/* 网站介绍样式 */
#website-introduction {
  padding: 48px 40px;
  background-color: #ffffff;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  border-radius: 12px;
  margin: 40px auto;
  max-width: 1200px;
  transition: all 0.3s ease;
  scroll-margin-top: 80px;
}

#website-introduction:hover {
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
  transform: translateY(-2px);
}

#website-introduction h1 {
  margin-bottom: 24px;
  color: #1a2a44;
  font-size: 36px;
  font-weight: 800;
  line-height: 1.2;
  letter-spacing: -0.5px;
  text-align: center;
}

#website-introduction h2 {
  margin: 32px 0 16px;
  color: #1a2a44;
  font-size: 26px;
  font-weight: 700;
  line-height: 1.3;
}

#website-introduction h3 {
  margin: 24px 0 12px;
  color: #3b82f6;
  font-size: 22px;
  font-weight: 600;
  line-height: 1.4;
}

#website-introduction h4 {
  margin: 16px 0 8px;
  color: #4b5e77;
  font-size: 18px;
  font-weight: 600;
}

#website-introduction p {
  font-size: 18px;
  color: #4b5e77;
  line-height: 1.8;
  margin-bottom: 16px;
  max-width: 800px;
  margin-left: auto;
  margin-right: auto;
}

#website-introduction ul {
  list-style-type: none;
  margin: 16px 0;
  padding-left: 24px;
}

#website-introduction ul li {
  position: relative;
  margin-bottom: 16px;
  font-size: 18px;
  color: #4b5e77;
  line-height: 1.8;
  padding-left: 32px;
}

#website-introduction ul li:before {
  content: "🎵";
  position: absolute;
  left: 0;
  color: #3b82f6;
  font-size: 20px;
}

#website-introduction ul li h3,
#website-introduction ul li h4 {
  display: inline;
}

/* 科学支持板块样式 */
#scientific-support {
  padding: 40px;
  background-color: #ffffff;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
  scroll-margin-top: 20px;
}

#scientific-support:hover {
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
}

.scientific-container {
  display: flex;
  gap: 20px;
  padding: 0 20px;
}

.video-container {
  flex: 1;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.video-container:hover {
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
}

.video-container iframe {
  width: 100%;
  height: 315px;
  border: none;
}

.text-links-container {
  flex: 1;
}

.text-links-container a {
  display: block;
  margin-bottom: 10px;
  color: #333;
  text-decoration: none;
  transition: color 0.3s ease;
  position: relative;
}

.text-links-container a::after {
  content: "";
  position: absolute;
  bottom: -2px;
  left: 0;
  width: 0;
  height: 2px;
  background-color: #76cff8;
  transition: width 0.3s ease;
}

.text-links-container a:hover {
  color: #92beed;
}

.text-links-container a:hover::after {
  width: 100%;
}

/* 疗愈资讯样式 */
#healing-news {
  padding: 40px;
  background-color: #ffffff;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
  scroll-margin-top: 20px;
}

#healing-news:hover {
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
}

#healing-news h2 {
  margin-bottom: 30px;
  color: #333;
  font-size: 28px;
  text-align: center;
  font-weight: 700;
}

.news-container {
  display: flex;
  overflow-x: auto;
  scroll-snap-type: x mandatory;
  gap: 20px;
  -webkit-overflow-scrolling: touch;
  padding: 0 20px;
}

.news-container::-webkit-scrollbar {
  height: 8px;
}

.news-container::-webkit-scrollbar-thumb {
  background-color: #ccc;
  border-radius: 4px;
}

.news-container::-webkit-scrollbar-track {
  background-color: #f1f1f1;
  border-radius: 4px;
}

.news-item {
  position: relative;
  min-width: 320px;
  cursor: pointer;
  transition: transform 0.3s ease;
  scroll-snap-align: start;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
}

.news-item:hover {
  transform: scale(1.02);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
}

.news-item img {
  width: 100%;
  height: auto;
  border-radius: 12px;
  filter: brightness(0.7);
}

.news-item p {
  position: absolute;
  top: 10px;
  left: 10px;
  font-size: 16px;
  color: white;
  line-height: 1.6;
  z-index: 1;
}

/* 分割线样式 */
.divider {
  border: none;
  border-top: 1px solid #86b6cd;
  margin: 40px 200px;
  position: relative;
}

.divider-title {
  position: absolute;
  top: -18px;
  left: 50%;
  transform: translateX(-50%);
  background-color: white;
  padding: 0 20px;
  color: #333;
  font-size: 28px;
  font-weight: 700;
}

/* 课程推荐样式 */
#course-recommendation {
  padding: 40px;
  background-color: #ffffff;
  overflow: hidden;
  position: relative;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
  scroll-margin-top: 20px;
}

#course-recommendation:hover {
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
}

.course-container {
  display: flex;
  gap: 20px;
  overflow-x: auto;
  scroll-snap-type: x mandatory;
  -webkit-overflow-scrolling: touch;
  padding: 0 20px;
}

.course-container::-webkit-scrollbar {
  height: 8px;
}

.course-container::-webkit-scrollbar-thumb {
  background-color: #ccc;
  border-radius: 4px;
}

.course-container::-webkit-scrollbar-track {
  background-color: #f1f1f1;
  border-radius: 4px;
}

.course-item {
  min-width: 320px;
  margin: 0;
  padding: 25px;
  border: 1px solid #f0f0f0;
  border-radius: 12px;
  background: linear-gradient(to bottom, #ffffff, #f9f9f9);
  cursor: pointer;
  transition: transform 0.3s ease;
  scroll-snap-align: start;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
}

.course-item:hover {
  transform: scale(1.02);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
}

.course-item h3 {
  margin-bottom: 12px;
  color: #a6caef;
  font-size: 22px;
  font-weight: 600;
}

.course-item p {
  font-size: 16px;
  color: #666;
  line-height: 1.6;
}

/* 模态框样式 */
.modal {
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0, 0, 0, 0.4);
  animation: fadeInModal 0.3s ease;
}

@keyframes fadeInModal {
  from {
    opacity: 0;
  }

  to {
    opacity: 1;
  }
}

.modal-content {
  background-color: #fefefe;
  margin: 15% auto;
  padding: 30px;
  border: none;
  width: 80%;
  max-width: 500px;
  border-radius: 12px;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
  animation: slideInModal 0.3s ease;
}

@keyframes slideInModal {
  from {
    transform: translateY(-50px);
  }

  to {
    transform: translateY(0);
  }
}

.close {
  color: #aaa;
  float: right;
  font-size: 32px;
  font-weight: bold;
  transition: color 0.3s ease;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}

/* 右侧固定栏样式 */
#fixed-sidebar {
  position: fixed;
  top: 70%;
  right: 20px;
  transform: translateY(-50%);
  background-color: rgba(255, 255, 255, 0.8);
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 15px;
  z-index: 10;
}

#fixed-sidebar ul {
  list-style-type: none;
}

#fixed-sidebar ul li {
  margin-bottom: 10px;
}

#fixed-sidebar ul li a {
  color: #333;
  text-decoration: none;
  transition: color 0.3s ease;
}

#fixed-sidebar ul li a:hover {
  color: #92beed;
}

/* 响应式设计 */
@media (max-width: 768px) {
  #slider-container {
    height: 300px;
  }

  nav {
    gap: 20px;
  }

  nav ul {
    gap: 20px;
  }

  #search-input {
    width: 200px;
  }

  #search-input:focus {
    width: 250px;
  }

  .divider {
    margin: 20px 20px;
  }
}

/* 页脚样式补充 */
footer {
  transition: all 0.3s ease;
}

footer:hover {
  box-shadow: 0 -4px 12px rgba(0, 0, 0, 0.1);
}

footer a {
  position: relative;
}

footer a::after {
  content: "";
  position: absolute;
  bottom: -2px;
  left: 0;
  width: 0;
  height: 2px;
  background-color: #3b82f6;
  transition: width 0.3s ease;
}

footer a:hover::after {
  width: 100%;
}

@media (max-width: 768px) {
  footer .grid {
    grid-template-columns: 1fr;
    text-align: center;
  }

  footer .flex {
    justify-content: center;
  }

  footer ul li {
    justify-content: center;
  }
}

@media (max-width: 480px) {
  footer {
    padding: 24px 16px;
  }

  footer h3 {
    font-size: 20px;
  }

  footer p,
  footer ul li,
  footer a,
  footer span {
    font-size: 14px;
  }

  footer .mt-4 {
    gap: 12px;
  }
}
</style>
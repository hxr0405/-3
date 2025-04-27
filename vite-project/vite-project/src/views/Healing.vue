<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

// 轮播图相关数据
const slides = ref([
  { src: '/src/assets/疗愈image/疗愈1.jpg', alt: '音乐介绍 1' },
  { src: '/src/assets/疗愈image/疗愈2.jpg', alt: '音乐介绍 2' },
  { src: '/src/assets/疗愈image/疗愈3.jpg', alt: '音乐介绍 3' }
]);
const currentSlide = ref(0);
let slideInterval = null;

// 搜索相关数据
const searchInput = ref('');
const searchResults = ref([]);
const showSearchSuggestions = ref(false);
const showCategoryList = ref(false);
const searchContainerFocused = ref(false);
const currentCategory = ref('全部');

// 模拟搜索建议
const mockSuggestions = {
  "全部": [
    "经典老歌", "经典老歌500首大全", "经典粤语老歌", "经典老歌dj",
    "经典老歌500首大全80后", "经典粤语", "经典英文老歌", "经典",
    "经典歌曲", "经典dj"
  ],
  "自然音乐": ["自然音乐1", "自然音乐2", "自然音乐3"],
  "古典音乐": ["古典音乐1", "古典音乐2", "古典音乐3"],
  "流行音乐": ["流行音乐1", "流行音乐2", "流行音乐3"]
};

// 过滤后的搜索建议
const filteredSuggestions = ref([]);

// 轮播图方法
function changeSlide(index) {
  currentSlide.value = index;
  resetSlideInterval();
}

function prevSlide() {
  currentSlide.value = (currentSlide.value - 1 + slides.value.length) % slides.value.length;
  resetSlideInterval();
}

function nextSlide() {
  currentSlide.value = (currentSlide.value + 1) % slides.value.length;
  resetSlideInterval();
}

function startSlideInterval() {
  slideInterval = setInterval(() => {
    nextSlide();
  }, 5000);
}

function resetSlideInterval() {
  clearInterval(slideInterval);
  startSlideInterval();
}

// 搜索相关方法
function toggleCategoryList() {
  showCategoryList.value = !showCategoryList.value;
}

function selectCategory(category) {
  currentCategory.value = category;
  showCategoryList.value = false;
  updateSuggestions();
}

function updateSuggestions() {
  const inputValue = searchInput.value.toLowerCase();
  const suggestions = mockSuggestions[currentCategory.value];
  
  if (inputValue && suggestions) {
    filteredSuggestions.value = suggestions.filter(suggestion => 
      suggestion.toLowerCase().includes(inputValue)
    );
    showSearchSuggestions.value = filteredSuggestions.value.length > 0;
  } else {
    showSearchSuggestions.value = false;
  }
}

function selectSuggestion(suggestion) {
  searchInput.value = suggestion;
  showSearchSuggestions.value = false;
  performSearch();
}

function focusSearchInput() {
  searchContainerFocused.value = true;
}

function blurSearchInput() {
  // 延迟失焦，允许点击建议
  setTimeout(() => {
    searchContainerFocused.value = false;
  }, 200);
}

function performSearch() {
  const searchTerm = searchInput.value;
  if (!searchTerm) return;
  
  // 模拟搜索结果
  const mockResults = [];
  const musicGenres = ['舒缓古典', '自然之声', '现代放松', '冥想音乐', '民谣治愈'];
  
  for (let i = 0; i < 10; i++) {
    mockResults.push({
      id: i + 1,
      title: `搜索结果：${musicGenres[i % musicGenres.length]} - ${i + 1}`,
      artist: '示例艺术家',
      img: `https://picsum.photos/200/150?random=${i + 1}`
    });
  }
  
  searchResults.value = mockResults.filter(result =>
    result.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
    result.artist.toLowerCase().includes(searchTerm.toLowerCase())
  );
}

// 导航方法
function navigateTo(path) {
  router.push(path);
}

// 平台特色项点击效果
function featureClickEffect(index) {
  // 在实际Vue应用中可以通过CSS类实现而不是直接操作DOM
  // 这里仅作示例
  console.log(`Feature ${index} clicked`);
}

// 创建点击外部关闭下拉菜单的处理函数
function handleClickOutside(event) {
  const searchContainer = document.getElementById('search-container');
  const categoryToggle = document.getElementById('category-toggle');
  
  if (searchContainer && !searchContainer.contains(event.target)) {
    showSearchSuggestions.value = false;
  }
  
  if (categoryToggle && !categoryToggle.contains(event.target)) {
    showCategoryList.value = false;
  }
}

// 生命周期钩子
onMounted(() => {
  startSlideInterval();
  document.addEventListener('click', handleClickOutside);
});

onUnmounted(() => {
  if (slideInterval) {
    clearInterval(slideInterval);
  }
  document.removeEventListener('click', handleClickOutside);
});
</script>

<template>
  <div class="healing-container">
    <!-- 导航栏 -->
    <nav>
      <div class="nav-left">
        <h1>HEARTCURE</h1>
        <div class="music-healing-description">音乐抚慰心灵</div>
        <a @click="navigateTo('/')" style="margin-left: auto;" class="nav-link">
          <i class="fa-solid fa-house"></i> 首页
        </a>
      </div>
    </nav>

    <!-- 音乐介绍图片滑动 -->
    <div class="hero-slider">
      <img 
        v-for="(slide, index) in slides" 
        :key="index"
        :src="slide.src" 
        :alt="slide.alt" 
        :class="{ 'active': index === currentSlide }"
      >
      <div class="slider-dots">
        <div 
          v-for="(slide, index) in slides" 
          :key="index"
          class="dot" 
          :class="{ 'active': index === currentSlide }"
          @click="changeSlide(index)"
        ></div>
      </div>
      <div class="slider-arrow left" @click="prevSlide">
        <i class="fa-solid fa-chevron-left"></i>
      </div>
      <div class="slider-arrow right" @click="nextSlide">
        <i class="fa-solid fa-chevron-right"></i>
      </div>
    </div>

    <!-- 音乐搜索 -->
    <section class="section">
      <h2>音乐搜索</h2>
      <div 
        class="search-container" 
        id="search-container"
        :class="{ 'focused': searchContainerFocused }"
      >
        <button 
          class="category-toggle" 
          id="category-toggle"
          @click="toggleCategoryList"
        >
          <i class="fa-solid fa-caret-down"></i>
        </button>
        <input 
          type="text" 
          id="search-input" 
          placeholder="搜索指定内容" 
          v-model="searchInput"
          @input="updateSuggestions"
          @focus="focusSearchInput"
          @blur="blurSearchInput"
          @click="navigateTo('/music-search')"
        >
        <button id="search-button" @click="performSearch">
          <i class="fa-solid fa-magnifying-glass"></i> 搜索
        </button>
        
        <!-- 搜索建议 -->
        <div 
          class="search-suggestions" 
          v-if="showSearchSuggestions"
        >
          <div 
            v-for="(suggestion, index) in filteredSuggestions" 
            :key="index"
            class="suggestion-item"
            @click="selectSuggestion(suggestion)"
          >
            {{ suggestion }}
          </div>
        </div>
        
        <!-- 分类列表 -->
        <div 
          class="category-list" 
          v-if="showCategoryList"
        >
          <div 
            v-for="category in ['全部', '治疗目标', '音乐类型', '聆听场景']"
            :key="category"
            class="category-list-item"
            @click="selectCategory(category)"
          >
            {{ category }}
          </div>
        </div>
      </div>
      
      <!-- 搜索结果 -->
      <div id="search-results">
        <div 
          v-for="result in searchResults" 
          :key="result.id"
          class="search-result-item"
        >
          <img :src="result.img" :alt="result.title">
          <h3>{{ result.title }}</h3>
          <p>艺术家：{{ result.artist }}</p>
          <button @click="navigateTo(`/play-page?id=${result.id}`)">播放</button>
        </div>
        <div v-if="searchResults.length === 0 && searchInput">
          没有找到相关的音乐。
        </div>
      </div>
    </section>

    <!-- 疗愈音乐库 -->
    <section class="section">
      <h2>疗愈音乐库</h2>
      <div class="music-library" @click="navigateTo('/healing-music-library')">
        <img src="/src/assets/疗愈image/资源库.jpg" alt="疗愈音乐库">
        <div class="music-library-overlay">
          <h3>疗愈音乐库</h3>
          <p>探索丰富的疗愈音乐，舒缓你的身心。</p>
        </div>
      </div>
      <p class="mt-4">我们的疗愈音乐库涵盖了各种风格的音乐，从古典的悠扬到现代的舒缓，每一首都精心挑选，旨在为你带来极致的放松体验。</p>
    </section>

    <!-- AI 助愈 -->
    <section class="section">
      <div class="ai-healing">
        <h2>AI 助愈</h2>
        <p>借助先进的 AI 技术，为你带来个性化的疗愈体验。我们的 AI 系统会分析你的情绪、喜好和音乐历史，为你量身定制专属的音乐列表。</p>
        <button @click="navigateTo('/ai-healing')">点击进入 AI 疗愈</button>
      </div>
    </section>

    <!-- 平台特色 -->
    <section class="section">
      <h2>平台特色</h2>
      <div class="feature-section">
        <div 
          v-for="(feature, index) in [
            {
              title: '丰富音乐库',
              description: '拥有海量疗愈音乐，涵盖各种风格和类型，满足你的不同需求。无论是想要放松身心、缓解压力还是提升睡眠质量，我们都有适合你的音乐。'
            },
            {
              title: '个性化推荐',
              description: '通过 AI 算法，根据你的喜好和情绪，为你推荐最适合的疗愈音乐。每次打开平台，都能发现新的音乐宝藏。'
            },
            {
              title: '专业指导',
              description: '提供专业的音乐疗愈指导，帮助你更好地利用音乐进行身心疗愈。我们的专家团队会定期分享音乐疗愈的知识和技巧。'
            }
          ]"
          :key="index"
          class="feature-item"
          @click="featureClickEffect(index)"
        >
          <h3>{{ feature.title }}</h3>
          <p>{{ feature.description }}</p>
        </div>
      </div>
    </section>

    <!-- 页脚 -->
    <footer>
      &copy; 2025 音乐疗愈平台. 保留所有权利.
    </footer>
  </div>
</template>

<style scoped>
.healing-container {
  font-family: 'Inter', sans-serif;
  background: linear-gradient(to bottom, #f5f9fc, #e6f4f9);
  margin: 0;
  padding: 0;
  color: #555;
}

nav {
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 2rem;
  position: sticky;
  top: 0;
  z-index: 10;
}

nav h1 {
  color: #3897d3;
  font-size: 1.8rem;
  margin: 0;
  font-weight: 600;
}

.nav-link {
  color: #3897d3;
  text-decoration: none;
  transition: color 0.3s ease;
  font-size: 1.1rem;
  margin-left: 1.5rem;
  cursor: pointer;
}

.nav-link:hover {
  color: #2a77a8;
}

.hero-slider {
  position: relative;
  height: 400px;
  overflow: hidden;
}

.hero-slider img {
  width: 100%;
  height: 400px;
  object-fit: cover;
  position: absolute;
  opacity: 0;
  transition: opacity 0.5s ease;
}

.hero-slider img.active {
  opacity: 1;
}

.slider-dots {
  position: absolute;
  bottom: 1rem;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
}

.slider-dots .dot {
  width: 12px;
  height: 12px;
  background-color: rgba(255, 255, 255, 0.5);
  border-radius: 50%;
  margin: 0 6px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.slider-dots .dot.active {
  background-color: #fff;
}

.slider-arrow {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background-color: rgba(0, 0, 0, 0.3);
  color: #fff;
  font-size: 1.8rem;
  padding: 0.6rem;
  cursor: pointer;
  z-index: 5;
  border-radius: 4px;
  transition: background-color 0.3s ease;
}

.slider-arrow.left {
  left: 1.5rem;
}

.slider-arrow.right {
  right: 1.5rem;
}

.slider-arrow:hover {
  background-color: rgba(0, 0, 0, 0.5);
}

.section {
  padding: 4rem 2rem;
  text-align: center;
  border-bottom: 1px solid #dcebf5;
}

.section h2 {
  color: #3897d3;
  font-size: 2.2rem;
  margin-bottom: 1.5rem;
  font-weight: 600;
  display: flex;
  align-items: center;
  justify-content: center;
}

.section h2::before,
.section h2::after {
  content: "";
  display: block;
  width: 120px;
  height: 2px;
  background-color: #3897d3;
  margin: 0 15px;
}

.section p {
  color: #668599;
  font-size: 1.1rem;
  line-height: 1.7;
  margin-bottom: 2rem;
  max-width: 800px;
  margin-left: auto;
  margin-right: auto;
}

.music-library {
  position: relative;
  width: 80%;
  max-width: 800px;
  margin: 0 auto;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.08);
  transition: transform 0.3s ease;
  cursor: pointer;
}

.music-library:hover {
  transform: scale(1.05);
}

.music-library img {
  width: 100%;
  height: auto;
  display: block;
}

.music-library-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.4);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.music-library:hover .music-library-overlay {
  opacity: 1;
}

.music-library-overlay h3 {
  color: #fff;
  font-size: 1.8rem;
  margin-bottom: 1rem;
  font-weight: 600;
}

.music-library-overlay p {
  color: #fff;
  font-size: 1.1rem;
  text-align: center;
  padding: 0 2rem;
}

.ai-healing {
  background-color: #fff;
  border-radius: 16px;
  padding: 3rem;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.08);
  transition: transform 0.3s ease;
  max-width: 800px;
  margin: 0 auto;
}

.ai-healing:hover {
  transform: translateY(-5px);
}

.ai-healing h2 {
  color: #3897d3;
  font-size: 2.2rem;
  margin-bottom: 1.5rem;
  font-weight: 600;
  display: flex;
  align-items: center;
  justify-content: center;
}

.ai-healing h2::before,
.ai-healing h2::after {
  content: "";
  display: block;
  width: 120px;
  height: 2px;
  background-color: #3897d3;
  margin: 0 15px;
}

.ai-healing p {
  color: #668599;
  font-size: 1.1rem;
  line-height: 1.7;
  margin-bottom: 2rem;
}

.ai-healing button {
  background: linear-gradient(to right, #3897d3, #2a77a8);
  color: #fff;
  border: none;
  border-radius: 8px;
  padding: 1rem 2rem;
  font-size: 1.1rem;
  cursor: pointer;
  transition: all 0.3s ease;
}

.ai-healing button:hover {
  background: linear-gradient(to right, #2a77a8, #1e587c);
  transform: scale(1.05);
}

footer {
  background-color: #fff;
  text-align: center;
  padding: 1rem;
  color: #668599;
  border-top: 1px solid #dcebf5;
}

#search-results {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1.5rem;
  margin-top: 2rem;
}

.search-result-item {
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.08);
  overflow: hidden;
  transition: transform 0.3s ease;
}

.search-result-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.12);
}

.search-result-item img {
  width: 100%;
  height: 150px;
  object-fit: cover;
}

.search-result-item h3 {
  color: #3897d3;
  font-size: 1.1rem;
  margin: 0.5rem;
  font-weight: 600;
}

.search-result-item p {
  color: #668599;
  font-size: 0.9rem;
  margin: 0 0.5rem 0.5rem;
}

.search-result-item button {
  background: linear-gradient(to right, #3897d3, #2a77a8);
  color: #fff;
  border: none;
  border-radius: 4px;
  padding: 0.5rem 1rem;
  margin: 0.5rem;
  cursor: pointer;
  transition: all 0.3s ease;
}

.search-result-item button:hover {
  background: linear-gradient(to right, #2a77a8, #1e587c);
  transform: scale(1.05);
}

.search-container {
  display: flex;
  align-items: center;
  background-color: #fff;
  padding: 12px;
  border-radius: 8px;
  margin: 20px auto;
  width: 80%;
  max-width: 800px;
  position: relative;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
}

.search-container.focused {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transform: translateY(-2px);
}

#search-input {
  flex: 1;
  background-color: transparent;
  border: none;
  color: #668599;
  font-size: 16px;
  outline: none;
  padding-left: 10px;
  transition: all 0.3s ease;
}

#search-input:focus {
  padding-left: 20px;
}

#search-button {
  background-color: transparent;
  border: none;
  color: #3897d3;
  font-size: 16px;
  cursor: pointer;
  padding: 0 10px;
  transition: all 0.3s ease;
}

#search-button:hover {
  transform: scale(1.1);
}

.category-toggle {
  background-color: transparent;
  border: none;
  color: #3897d3;
  font-size: 16px;
  cursor: pointer;
  padding: 0 10px;
  transition: all 0.3s ease;
}

.category-toggle:hover {
  transform: scale(1.1);
}

.category-list {
  position: absolute;
  top: 100%;
  left: 0;
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 8px;
  z-index: 10;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  animation: fadeIn 0.3s ease;
}

.category-list-item {
  padding: 12px 20px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.category-list-item:hover {
  background-color: #e0f7fa;
}

.search-suggestions {
  position: absolute;
  top: 100%;
  left: 0;
  width: 100%;
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 8px;
  max-height: 200px;
  overflow-y: auto;
  z-index: 10;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  animation: fadeIn 0.3s ease;
}

.suggestion-item {
  padding: 12px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.suggestion-item:hover {
  background-color: #e0f7fa;
}

.feature-section {
  display: flex;
  justify-content: center;
  gap: 2rem;
  flex-wrap: wrap;
  margin-top: 2rem;
}

.feature-item {
  background-color: #fff;
  border-radius: 16px;
  padding: 2rem;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;
  width: 300px;
  cursor: pointer;
}

.feature-item:hover {
  transform: translateY(-10px) scale(1.05);
  box-shadow: 0 12px 24px rgba(0, 0, 0, 0.12);
}

.feature-item:active {
  transform: translateY(-5px) scale(1.02);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.12);
}

.feature-item h3 {
  color: #3897d3;
  font-size: 1.5rem;
  margin-bottom: 1rem;
  font-weight: 600;
}

.feature-item p {
  color: #668599;
  font-size: 1rem;
  line-height: 1.6;
}

.music-healing-description {
  color: #668599;
  font-size: 1.2rem;
  text-align: center;
  width: 100%;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.nav-left {
  display: flex;
  align-items: center;
  position: relative;
  width: 100%;
}

/* 导航栏链接动画效果 */
.nav-link {
  position: relative;
}

.nav-link::after {
  content: '';
  position: absolute;
  width: 100%;
  transform: scaleX(0);
  height: 2px;
  bottom: -3px;
  left: 0;
  background-color: #2a77a8;
  transform-origin: bottom right;
  transition: transform 0.3s ease-out;
}

.nav-link:hover::after {
  transform: scaleX(1);
  transform-origin: bottom left;
}

/* 动画 */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 响应式设计 */
@media (max-width: 768px) {
  .section h2::before,
  .section h2::after,
  .ai-healing h2::before,
  .ai-healing h2::after {
    width: 60px;
  }

  .search-container {
    width: 95%;
  }
  
  .music-library {
    width: 95%;
  }
  
  .feature-item {
    width: 100%;
  }
}
</style>
<script setup>
import { ref, reactive, computed, onMounted, nextTick } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const sidebarOpen = ref(false);
const isMobile = ref(false);
const searchInput = ref('');
const searchResults = ref([]);
const searchHistory = ref([]);
const hotSpots = [
  '古典音乐',
  '治愈系歌曲',
  '舒缓压力音乐',
];
const mockPosts = reactive([
  {
    id: 1,
    author: '音乐疗愈师',
    avatar: 'M',
    time: '2小时前',
    content: '今天分享一首巴赫的G大调大提琴组曲，特别适合在清晨聆听，能带来一整天的平静与专注。',
    music: { title: 'G大调大提琴组曲, BWV 1007', artist: '巴赫' },
    emotions: ['平静', '专注'],
    likes: 42,
    comments: 8,
    shares: 3,
    liked: false
  },
  {
    id: 2,
    author: '心灵治愈者',
    avatar: 'H',
    time: '5小时前',
    content: '最近发现这首《Weightless》真的能有效降低焦虑水平，科学研究表明它能减少65%的焦虑感！',
    music: { title: 'Weightless', artist: 'Marconi Union' },
    emotions: ['放松', '平静'],
    likes: 89,
    comments: 15,
    shares: 22,
    liked: false
  },
  {
    id: 3,
    author: '音乐探索者',
    avatar: 'E',
    time: '1天前',
    content: '分享一首我最近很喜欢的民谣《Five Hundred Miles》，每次听都能让我想起家乡的温暖。',
    music: { title: 'Five Hundred Miles', artist: 'The Innocence Mission' },
    emotions: ['怀旧', '感伤'],
    likes: 56,
    comments: 12,
    shares: 5,
    liked: false
  }
]);

function checkMobile() {
  isMobile.value = window.innerWidth <= 768;
}
function openSidebar() {
  sidebarOpen.value = !sidebarOpen.value;
}
function closeSidebar() {
  if (isMobile.value) sidebarOpen.value = false;
}
function loadHistory() {
  const local = localStorage.getItem('searchHistory');
  searchHistory.value = local ? JSON.parse(local) : [];
}
function saveHistory() {
  localStorage.setItem('searchHistory', JSON.stringify(searchHistory.value));
}
function addHistory(keyword) {
  if (!searchHistory.value.includes(keyword)) {
    searchHistory.value.unshift(keyword);
    if (searchHistory.value.length > 5) searchHistory.value.pop();
    saveHistory();
  }
}
function performSearch(keyword = null) {
  const term = (keyword || searchInput.value).trim();
  if (!term) return;
  addHistory(term);
  searchResults.value = mockPosts.filter(post =>
    post.author.includes(term) ||
    post.content.includes(term) ||
    post.music.title.includes(term) ||
    post.music.artist.includes(term) ||
    post.emotions.some(e => e.includes(term))
  );
}
function clickHot(keyword) {
  searchInput.value = keyword;
  performSearch(keyword);
}
function likePost(post) {
  post.liked = !post.liked;
  post.likes += post.liked ? 1 : -1;
}
function playToggle(e) {
  const icon = e.target.closest('button').querySelector('i');
  if (icon.classList.contains('fa-play')) {
    icon.classList.remove('fa-play');
    icon.classList.add('fa-pause');
  } else {
    icon.classList.remove('fa-pause');
    icon.classList.add('fa-play');
  }
}
onMounted(() => {
  checkMobile();
  window.addEventListener('resize', checkMobile);
  loadHistory();
});
</script>

<template>
  <div>
    <header>
      <div id="logo"><router-link to="/">HEARTCURE</router-link></div>
      <div id="header-description">在社交中感受音乐疗愈的温暖与力量</div>
      <router-link id="home-link" to="/">
        <i class="fa-solid fa-house"></i> 首页
      </router-link>
    </header>
    <div id="fixed-sidebar" :style="sidebarOpen ? 'transform: translateX(0)' : ''" @click.self="closeSidebar">
      <ul>
        <li><router-link to="/recommended"><i class="fa-solid fa-star"></i> 推荐</router-link></li>
        <li><router-link to="/write-post"><i class="fa-solid fa-pen-to-square"></i> 写帖子</router-link></li>
        <li><router-link to="/my-groups"><i class="fa-solid fa-users"></i> 分组疗愈</router-link></li>
        <li><router-link to="/search-posts"><i class="fa-solid fa-magnifying-glass"></i> 查找帖子</router-link></li>
      </ul>
    </div>
    <div id="main-content">
      <div class="search-container">
        <div class="search-box">
          <input type="text" class="search-input" v-model="searchInput" placeholder="搜索音乐、帖子或用户..." @keyup.enter="performSearch" />
          <button class="search-button" @click="performSearch">
            <i class="fas fa-search"></i>
          </button>
        </div>
        <button class="filter-button">
          <i class="fas fa-sliders-h"></i> 筛选
        </button>
      </div>
      <div class="history-card">
        <div class="card-title">
          <i class="fas fa-history"></i> 搜索历史
        </div>
        <ul class="history-list">
          <li v-if="searchHistory.length === 0" style="color: var(--gray); font-size: 0.875rem; padding: 0.5rem;">暂无搜索历史</li>
          <li v-for="keyword in searchHistory" :key="keyword" class="history-item">
            <a href="#" @click.prevent="performSearch(keyword)"><i class="fas fa-search"></i> {{ keyword }}</a>
          </li>
        </ul>
      </div>
      <div class="history-card">
        <div class="card-title">
          <i class="fas fa-fire"></i> 热门搜索
        </div>
        <ul class="history-list">
          <li v-for="hot in hotSpots" :key="hot" class="history-item">
            <a href="#" @click.prevent="clickHot(hot)"><i class="fas fa-search"></i> {{ hot }}</a>
          </li>
        </ul>
      </div>
      <div class="search-results">
        <div v-if="searchResults.length === 0" class="no-results">
          <div class="no-results-icon">
            <i class="fas fa-music"></i>
          </div>
          <h3>没有找到相关帖子</h3>
          <p>尝试使用不同的关键词</p>
        </div>
        <div v-for="post in searchResults" :key="post.id" class="post-card">
          <div class="post-header">
            <div class="post-avatar">{{ post.avatar }}</div>
            <div>
              <div class="post-author">{{ post.author }}</div>
              <div class="post-time">{{ post.time }}</div>
            </div>
          </div>
          <div class="post-content">{{ post.content }}</div>
          <div class="music-card">
            <div class="music-cover">
              <i class="fas fa-music"></i>
            </div>
            <div class="music-info">
              <div class="music-title">{{ post.music.title }}</div>
              <div class="music-artist">{{ post.music.artist }}</div>
            </div>
            <button class="play-button" @click="playToggle">
              <i class="fas fa-play"></i>
            </button>
          </div>
          <div class="tags">
            <div v-for="emotion in post.emotions" :key="emotion" class="tag">{{ emotion }}</div>
          </div>
          <div class="post-actions">
            <div class="action-button" :class="{ 'text-blue-500': post.liked }" @click="likePost(post)">
              <i class="fas fa-thumbs-up"></i> {{ post.likes }}
            </div>
            <div class="action-button">
              <i class="fas fa-comment"></i> {{ post.comments }}
            </div>
            <div class="action-button">
              <i class="fas fa-share"></i> {{ post.shares }}
            </div>
          </div>
        </div>
      </div>
    </div>
    <button id="mobile-menu-button" @click="openSidebar" v-show="isMobile">
      <i class="fas fa-bars"></i>
    </button>
  </div>
</template>

<style scoped>
:root {
  --primary: #3b82f6;
  --primary-light: #93c5fd;
  --primary-dark: #1d4ed8;
  --gray-light: #f3f4f6;
  --gray: #6b7280;
  --gray-dark: #374151;
  --danger: #ef4444;
  --success: #10b981;
}
body {
  font-family: 'Inter', sans-serif;
  background-color: #f9fafb;
  color: var(--gray-dark);
  margin: 0;
  padding: 0;
  min-height: 100vh;
}
header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 2rem;
  background: white;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1000;
}
#logo {
  font-size: 1.5rem;
  font-weight: 700;
  color: var(--primary);
  letter-spacing: -0.025em;
}
#header-description {
  font-size: 0.875rem;
  color: var(--gray);
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
}
#home-link {
  color: var(--gray);
  text-decoration: none;
  font-size: 0.875rem;
  display: flex;
  align-items: center;
  gap: 0.5rem;
}
#home-link:hover {
  color: var(--primary);
}
#fixed-sidebar {
  position: fixed;
  top: 0;
  left: 0;
  width: 16rem;
  height: 100vh;
  background: white;
  box-shadow: 1px 0 3px rgba(0, 0, 0, 0.1);
  padding: 5rem 1rem 1rem;
  overflow-y: auto;
  z-index: 900;
  transition: transform 0.3s ease;
}
@media (max-width: 768px) {
  #fixed-sidebar {
    transform: translateX(-100%);
    width: 70%;
  }
  #main-content {
    margin-left: 0;
  }
}
#fixed-sidebar ul {
  list-style: none;
  padding: 0;
}
#fixed-sidebar li {
  margin-bottom: 0.5rem;
}
#fixed-sidebar a {
  color: var(--gray);
  text-decoration: none;
  display: flex;
  align-items: center;
  gap: 0.75rem;
  padding: 0.75rem;
  border-radius: 0.5rem;
}
#fixed-sidebar a:hover {
  background: var(--gray-light);
  color: var(--primary);
}
#main-content {
  margin-left: 16rem;
  padding: 5rem 2rem 2rem;
  transition: margin-left 0.3s ease;
}
.search-container {
  max-width: 800px;
  margin: 0 auto 2rem;
}
.search-box {
  position: relative;
  margin-bottom: 1rem;
}
.search-input {
  width: 100%;
  padding: 0.875rem 1rem;
  border: 1px solid var(--gray-light);
  border-radius: 0.5rem;
  font-size: 1rem;
  padding-right: 3.5rem;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
}
.search-input:focus {
  outline: none;
  border-color: var(--primary);
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}
.search-button {
  position: absolute;
  right: 0.5rem;
  top: 50%;
  transform: translateY(-50%);
  background: var(--primary);
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 0.375rem;
  cursor: pointer;
}
.search-button:hover {
  background: var(--primary-dark);
}
.filter-button {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  background: white;
  border: 1px solid var(--gray-light);
  border-radius: 0.5rem;
  padding: 0.5rem 1rem;
  cursor: pointer;
}
.filter-button:hover {
  background: var(--gray-light);
}
.search-results {
  max-width: 800px;
  margin: 0 auto;
}
.post-card {
  background: white;
  border-radius: 0.5rem;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  padding: 1.5rem;
  margin-bottom: 1.5rem;
  transition: all 0.2s ease;
}
.post-card:hover {
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transform: translateY(-2px);
}
.post-header {
  display: flex;
  align-items: center;
  margin-bottom: 1rem;
}
.post-avatar {
  width: 2.5rem;
  height: 2.5rem;
  border-radius: 50%;
  background: var(--gray-light);
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 1rem;
  color: var(--gray);
}
.post-author {
  font-weight: 600;
}
.post-time {
  font-size: 0.75rem;
  color: var(--gray);
}
.post-content {
  margin-bottom: 1.5rem;
  line-height: 1.6;
}
.music-card {
  background: rgba(59, 130, 246, 0.05);
  border-radius: 0.5rem;
  padding: 1rem;
  margin-bottom: 1rem;
  display: flex;
  align-items: center;
  gap: 1rem;
}
.music-cover {
  width: 3rem;
  height: 3rem;
  background: var(--gray-light);
  border-radius: 0.25rem;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--gray);
}
.music-info {
  flex-grow: 1;
}
.music-title {
  font-weight: 600;
  margin-bottom: 0.25rem;
}
.music-artist {
  font-size: 0.875rem;
  color: var(--gray);
}
.play-button {
  width: 2rem;
  height: 2rem;
  border-radius: 50%;
  background: var(--primary);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}
.tags {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  margin-bottom: 1rem;
}
.tag {
  background: var(--gray-light);
  color: var(--gray-dark);
  padding: 0.25rem 0.75rem;
  border-radius: 1rem;
  font-size: 0.75rem;
}
.post-actions {
  display: flex;
  gap: 1rem;
  border-top: 1px solid var(--gray-light);
  padding-top: 1rem;
}
.action-button {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  color: var(--gray);
  cursor: pointer;
}
.action-button:hover {
  color: var(--primary);
}
.history-card {
  background: white;
  border-radius: 0.5rem;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  padding: 1.5rem;
  margin-bottom: 1.5rem;
}
.card-title {
  font-weight: 600;
  margin-bottom: 1rem;
  display: flex;
  align-items: center;
  gap: 0.5rem;
}
.history-list {
  list-style: none;
  padding: 0;
}
.history-item {
  margin-bottom: 0.5rem;
}
.history-item a {
  color: var(--gray);
  text-decoration: none;
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.5rem;
  border-radius: 0.25rem;
}
.history-item a:hover {
  background: var(--gray-light);
  color: var(--primary);
}
#mobile-menu-button {
  display: none;
  position: fixed;
  bottom: 1.5rem;
  right: 1.5rem;
  width: 3.5rem;
  height: 3.5rem;
  border-radius: 50%;
  background: var(--primary);
  color: white;
  border: none;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  z-index: 1000;
  justify-content: center;
  align-items: center;
  font-size: 1.25rem;
  cursor: pointer;
}
@media (max-width: 768px) {
  #mobile-menu-button {
    display: flex;
  }
}
.no-results {
  text-align: center;
  padding: 2rem;
  color: var(--gray);
}
.no-results-icon {
  font-size: 2rem;
  margin-bottom: 1rem;
  color: var(--gray-light);
}
</style>
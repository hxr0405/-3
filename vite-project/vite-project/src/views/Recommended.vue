<script setup>
import { ref, reactive, computed, onMounted, nextTick } from 'vue';

const categories = [
  { label: '全部', value: 'all' },
  { label: '睡眠问题', value: 'sleep' },
  { label: '焦虑缓解', value: 'meditation' },
  { label: '情绪低落', value: 'emotion' },
  { label: '疗愈经验', value: 'stress' },
];
const activeCategory = ref('all');
const posts = ref([
  {
    author: '用户1',
    avatar: '',
    content: '最近尝试了一些音乐疗愈的方法，感觉心情真的有变好。我尝试在睡前听一些舒缓的古典音乐，如肖邦的夜曲，那种悠扬的旋律仿佛能将一天的疲惫都带走。慢慢地，我入睡变得更容易，睡眠质量也提高了不少。真的很推荐大家也试试这种音乐疗愈的方式！',
    categories: ['sleep'],
    liked: false,
    followed: false,
    commented: false,
    shared: false,
  },
  {
    author: '用户2',
    avatar: '',
    content: '偶然发现了一首非常适合冥想的音乐，叫做《故乡的原风景》。每次听这首曲子，我都仿佛置身于宁静的大自然中，周围是潺潺的溪流和清脆的鸟鸣。在冥想的时候听它，能让我的内心更加平静，思绪也更加清晰。希望能帮助到需要的人，大家也可以试试在冥想时播放这首音乐。',
    categories: ['meditation'],
    liked: false,
    followed: false,
    commented: false,
    shared: false,
  },
  {
    author: '用户3',
    avatar: '',
    content: '我平时压力很大，工作繁忙，心情总是很烦躁。后来朋友推荐我听一些轻松的爵士乐，比如戴夫·布鲁贝克的《Take Five》。这首曲子的节奏很有活力，每次听都能让我感到心情愉悦，压力也减轻了不少。大家如果也有压力大的情况，不妨试试听听这类音乐。',
    categories: ['stress'],
    liked: false,
    followed: false,
    commented: false,
    shared: false,
  },
  {
    author: '用户4',
    avatar: '',
    content: '音乐疗愈真的很神奇！我有一段时间情绪很低落，对什么都提不起兴趣。后来我开始听一些励志的摇滚音乐，像林肯公园的《Numb》，那激昂的旋律和歌词仿佛给了我力量，让我重新振作起来。从那以后，我就坚信音乐有着治愈心灵的能力。',
    categories: ['emotion'],
    liked: false,
    followed: false,
    commented: false,
    shared: false,
  },
]);
const showModal = ref(false);
const modalContent = ref('');
const currentTime = ref('');

function updateTime() {
  const now = new Date();
  const year = now.getFullYear();
  const month = String(now.getMonth() + 1).padStart(2, '0');
  const day = String(now.getDate()).padStart(2, '0');
  const hours = String(now.getHours()).padStart(2, '0');
  const minutes = String(now.getMinutes()).padStart(2, '0');
  const seconds = String(now.getSeconds()).padStart(2, '0');
  currentTime.value = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}

onMounted(() => {
  updateTime();
  setInterval(updateTime, 1000);
});

const filteredPosts = computed(() => {
  if (activeCategory.value === 'all') return posts.value;
  return posts.value.filter(post => post.categories.includes(activeCategory.value));
});

function setCategory(cat) {
  activeCategory.value = cat;
}
function toggleLike(idx) {
  posts.value[idx].liked = !posts.value[idx].liked;
}
function toggleComment(idx) {
  posts.value[idx].commented = !posts.value[idx].commented;
}
function toggleShare(idx) {
  posts.value[idx].shared = !posts.value[idx].shared;
}
function toggleFollow(idx) {
  posts.value[idx].followed = !posts.value[idx].followed;
}
function openWriteModal() {
  showModal.value = true;
  modalContent.value = '';
}
function closeWriteModal() {
  showModal.value = false;
}
function submitPost() {
  if (!modalContent.value.trim()) return;
  posts.value.unshift({
    author: '你',
    avatar: '',
    content: modalContent.value,
    categories: ['all'],
    liked: false,
    followed: false,
    commented: false,
    shared: false,
  });
  showModal.value = false;
  modalContent.value = '';
}
</script>

<template>
  <div>
    <header>
      <div id="logo"><a href="/">HEARTCURE</a></div>
      <div id="header-description">在社交中感受音乐疗愈的温暖与力量</div>
      <a id="home-link" href="/">
        <i class="fa-solid fa-house"></i> 首页
      </a>
    </header>
    <div id="fixed-sidebar">
      <ul>
        <li><router-link to="/recommended"><i class="fa-solid fa-star"></i> 推荐</router-link></li>
        <li><router-link to="/write-post"><i class="fa-solid fa-pen-to-square"></i> 写帖子</router-link></li>
        <li><router-link to="/my-groups"><i class="fa-solid fa-users"></i> 分组疗愈</router-link></li>
        <li><router-link to="/search-posts"><i class="fa-solid fa-magnifying-glass"></i> 查找帖子</router-link></li>
      </ul>
    </div>
    <div id="main-content">
      <div class="category-buttons">
        <button
          v-for="cat in categories"
          :key="cat.value"
          class="category-button"
          :class="{ active: activeCategory === cat.value }"
          @click="setCategory(cat.value)"
        >
          {{ cat.label }}
        </button>
      </div>
      <div v-for="(post, idx) in filteredPosts" :key="idx" class="recommended-post" :data-categories="post.categories.join(' ')">
        <div class="post-author">
          <div class="author-avatar"></div>
          <div class="author-name">{{ post.author }}</div>
          <button class="follow-button" :style="post.followed ? 'background-color: #a0aec0; box-shadow: none;' : ''" @click="toggleFollow(idx)">
            {{ post.followed ? '已关注' : '关注' }}
          </button>
        </div>
        <div class="post-content">
          <p>{{ post.content }}</p>
        </div>
        <div class="post-actions">
          <button class="action-button" :class="{ active: post.liked }" @click="toggleLike(idx)">
            <i class="fa-solid fa-thumbs-up"></i> 点赞
          </button>
          <button class="action-button" :class="{ active: post.commented }" @click="toggleComment(idx)">
            <i class="fa-solid fa-comment"></i> 评论
          </button>
          <button class="action-button" :class="{ active: post.shared }" @click="toggleShare(idx)">
            <i class="fa-solid fa-share"></i> 转发
          </button>
        </div>
      </div>
      <div style="margin-top:2rem; color:#718096; text-align:right;">当前时间：{{ currentTime }}</div>
    </div>
    <div v-if="showModal" class="modal" style="display: block;">
      <div class="modal-content">
        <span class="close" @click="closeWriteModal">&times;</span>
        <h2>写帖子</h2>
        <textarea v-model="modalContent" placeholder="在这里写下你的想法和体验" style="width:100%;height:100px;margin-bottom:1rem;"></textarea>
        <button id="modal-post-submit" @click="submitPost">发布</button>
      </div>
    </div>
  </div>
</template>

<style>
body {
  font-family: 'Inter', sans-serif;
  background: linear-gradient(135deg, #e9f6ff 0%, #f7f9fc 100%);
  color: #333;
  margin: 0;
  padding: 0;
}
header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1.5rem 3rem;
  background: rgba(255, 255, 255, 0.9);
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1000;
  backdrop-filter: blur(10px);
  transition: all 0.3s ease;
}
header:hover {
  box-shadow: 0 6px 30px rgba(0, 0, 0, 0.1);
}
#logo {
  font-size: 1.8rem;
  font-weight: 800;
  color: #2c7be5;
  letter-spacing: -0.05rem;
  transition: color 0.3s ease;
}
#logo:hover {
  color: #1a56db;
}
#header-description {
  font-size: 1rem;
  color: #718096;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  animation: fadeIn 1s ease-in-out;
}
@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}
#home-link {
  color: #718096;
  text-decoration: none;
  font-size: 1rem;
  display: flex;
  align-items: center;
  gap: 0.5rem;
  transition: color 0.3s ease;
}
#home-link:hover {
  color: #2c7be5;
}
#fixed-sidebar {
  position: fixed;
  top: 0;
  left: 0;
  width: 16rem;
  height: 100vh;
  background: rgba(255, 255, 255, 0.9);
  box-shadow: 4px 0 20px rgba(0, 0, 0, 0.05);
  padding: 5.5rem 1.5rem 1.5rem;
  overflow-y: auto;
  backdrop-filter: blur(10px);
  z-index: 900;
}
#fixed-sidebar ul {
  list-style-type: none;
}
#fixed-sidebar ul li {
  margin-bottom: 1rem;
}
#fixed-sidebar ul li a {
  color: #718096;
  text-decoration: none;
  font-size: 1rem;
  display: flex;
  align-items: center;
  gap: 0.75rem;
  padding: 0.75rem;
  border-radius: 0.5rem;
  transition: all 0.3s ease;
}
#fixed-sidebar ul li a:hover {
  background: #edf2f7;
  color: #2c7be5;
}
#main-content {
  margin-left: 16rem;
  padding: 7rem 3rem 3rem;
}
.category-buttons {
  display: flex;
  gap: 1rem;
  margin-bottom: 1.5rem;
}
.category-button {
  background: transparent;
  border: 1px solid #718096;
  color: #718096;
  padding: 0.5rem 1rem;
  border-radius: 0.5rem;
  cursor: pointer;
  transition: all 0.3s ease;
}
.category-button.active {
  background: #2c7be5;
  color: white;
  border-color: #2c7be5;
}
.recommended-post {
  background: white;
  border-radius: 1rem;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
  padding: 1.5rem;
  margin-bottom: 1.5rem;
  transition: all 0.3s ease;
}
.recommended-post:hover {
  box-shadow: 0 6px 30px rgba(0, 0, 0, 0.1);
  transform: translateY(-3px);
}
.post-author {
  display: flex;
  align-items: center;
  margin-bottom: 1rem;
}
.author-avatar {
  width: 2.5rem;
  height: 2.5rem;
  border-radius: 50%;
  background: #edf2f7;
  margin-right: 0.75rem;
}
.author-name {
  font-weight: 600;
  color: #2d3748;
}
.follow-button {
  margin-left: auto;
  background: #2c7be5;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 0.5rem;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 4px 10px rgba(44, 123, 229, 0.2);
}
.follow-button:hover {
  background: #1a56db;
  transform: translateY(-2px);
  box-shadow: 0 6px 15px rgba(44, 123, 229, 0.3);
}
.follow-button:active {
  transform: translateY(0);
  box-shadow: 0 4px 10px rgba(44, 123, 229, 0.2);
}
.post-content {
  margin-bottom: 1rem;
  color: #4a5568;
}
.post-actions {
  display: flex;
  gap: 1rem;
}
.action-button {
  background: transparent;
  border: none;
  color: #718096;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 0.25rem;
  transition: all 0.3s ease;
}
.action-button:hover {
  color: #2c7be5;
  transform: scale(1.05);
}
.action-button:active {
  transform: scale(0.95);
}
.modal {
  display: none;
  position: fixed;
  z-index: 1100;
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
  background-color: white;
  margin: 10% auto;
  padding: 2rem;
  border-radius: 1rem;
  width: 80%;
  max-width: 36rem;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
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
  color: #718096;
  float: right;
  font-size: 1.5rem;
  font-weight: bold;
  transition: color 0.3s ease;
}
.close:hover,
.close:focus {
  color: #2d3748;
  text-decoration: none;
  cursor: pointer;
}
</style>
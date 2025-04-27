<script setup>
import { ref, reactive, computed, onMounted, nextTick } from 'vue';

const sidebarOpen = ref(false);
const showToast = ref(false);
const toastMsg = ref('');
const toastType = ref('success');
const title = ref('');
const titleCount = computed(() => `${title.value.length}/100`);
const contentCount = ref('0/2000');
const postContent = ref('');
const quill = ref(null);
const selectedMusic = ref(null);
const uploadedFile = ref(null);
const musicList = [
  { id: 1, title: '宁静的海洋', artist: '自然之声' },
  { id: 2, title: '清晨鸟鸣', artist: '自然之声' },
  { id: 3, title: '森林冥想', artist: '冥想大师' },
];
const emotionTags = [
  { key: 'relaxed', label: '放松' },
  { key: 'happy', label: '快乐' },
  { key: 'sad', label: '悲伤' },
  { key: 'energetic', label: '充满活力' },
  { key: 'calm', label: '平静' },
  { key: 'anxious', label: '焦虑' },
  { key: 'focused', label: '专注' },
  { key: 'nostalgic', label: '怀旧' },
];
const selectedEmotions = ref([]);
const isSubmitting = ref(false);
const isMobile = ref(false);

function openSidebar() {
  sidebarOpen.value = !sidebarOpen.value;
}
function selectMusic(id) {
  selectedMusic.value = id;
  uploadedFile.value = null;
}
function onUploadClick() {
  document.getElementById('music-upload').click();
  selectedMusic.value = null;
}
function onFileChange(e) {
  const file = e.target.files[0];
  if (file) {
    uploadedFile.value = file;
    selectedMusic.value = null;
  } else {
    uploadedFile.value = null;
  }
}
function toggleEmotion(key) {
  if (selectedEmotions.value.includes(key)) {
    selectedEmotions.value = selectedEmotions.value.filter(k => k !== key);
  } else {
    selectedEmotions.value = [...selectedEmotions.value, key];
  }
}
function showToastMsg(msg, type = 'success') {
  toastMsg.value = msg;
  toastType.value = type;
  showToast.value = true;
  setTimeout(() => {
    showToast.value = false;
  }, 3000);
}
function onTitleInput(e) {
  nextTick(() => {
    if (title.value.length > 100) {
      title.value = title.value.slice(0, 100);
    }
  });
}
function onContentChange() {
  const text = quill.value.getText();
  const count = text.length - 1;
  contentCount.value = `${count}/2000`;
}
function onSubmit(e) {
  e.preventDefault();
  if (!title.value.trim()) {
    showToastMsg('请输入帖子标题', 'error');
    return;
  }
  const textContent = quill.value.getText().trim();
  if (!textContent) {
    showToastMsg('请输入帖子内容', 'error');
    return;
  }
  if (!selectedMusic.value && !uploadedFile.value) {
    showToastMsg('请选择关联音乐或上传音乐', 'error');
    return;
  }
  if (selectedEmotions.value.length === 0) {
    showToastMsg('请选择至少一个情绪标签', 'error');
    return;
  }
  isSubmitting.value = true;
  setTimeout(() => {
    showToastMsg('帖子发布成功！', 'success');
    title.value = '';
    quill.value.setContents([{ insert: '\n' }]);
    selectedMusic.value = null;
    uploadedFile.value = null;
    selectedEmotions.value = [];
    isSubmitting.value = false;
  }, 1500);
}
function onDraft() {
  if (!title.value && !quill.value.getText().trim()) {
    showToastMsg('没有内容可保存', 'error');
    return;
  }
  showToastMsg('草稿已保存', 'success');
}
function checkMobile() {
  isMobile.value = window.innerWidth <= 768;
}
function closeSidebar() {
  if (isMobile.value) sidebarOpen.value = false;
}
onMounted(() => {
  checkMobile();
  window.addEventListener('resize', checkMobile);
  nextTick(() => {
    if (!window.Quill) {
      showToastMsg('Quill 编辑器未加载，请检查网络或CDN引用顺序', 'error');
      return;
    }
    const editorDiv = document.getElementById('editor');
    if (editorDiv) editorDiv.innerHTML = '';
    quill.value = new window.Quill('#editor', {
      theme: 'snow',
      placeholder: '在这里写下您的音乐疗愈体验...',
      modules: {
        toolbar: [
          ['bold', 'italic', 'underline', 'strike'],
          ['blockquote', 'code-block'],
          [{ header: 1 }, { header: 2 }],
          [{ list: 'ordered' }, { list: 'bullet' }],
          [{ script: 'sub' }, { script: 'super' }],
          [{ indent: '-1' }, { indent: '+1' }],
          [{ direction: 'rtl' }],
          [{ size: ['small', false, 'large', 'huge'] }],
          [{ header: [1, 2, 3, 4, 5, 6, false] }],
          [{ color: [] }, { background: [] }],
          [{ font: [] }],
          [{ align: [] }],
          ['clean'],
          ['link', 'image']
        ]
      }
    });
    quill.value.enable(true);
    quill.value.on('text-change', onContentChange);
  });
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
      <div class="write-post-container">
        <div class="write-post-card">
          <h1 class="write-post-title">
            <i class="fas fa-pen-to-square"></i> 写帖子
          </h1>
          <form id="post-form" @submit="onSubmit">
            <div class="form-group">
              <label for="post-title" class="form-label">帖子标题</label>
              <input type="text" id="post-title" class="form-control" v-model="title" @input="onTitleInput" placeholder="为您的帖子取一个吸引人的标题" maxlength="100">
              <div class="character-count" :class="{ warning: title.length > 90 }">{{ titleCount }}</div>
            </div>
            <div class="form-group">
              <label class="form-label">帖子内容</label>
              <div id="editor"></div>
              <div class="character-count" :class="{ warning: contentCount.split('/')[0] > 1900 }">{{ contentCount }}</div>
            </div>
            <div class="form-group">
              <label class="form-label">关联音乐</label>
              <div class="music-selector" v-show="!uploadedFile">
                <div
                  v-for="music in musicList"
                  :key="music.id"
                  class="music-card"
                  :class="{ selected: selectedMusic === music.id }"
                  @click="selectMusic(music.id)"
                >
                  <div class="music-cover">
                    <i class="fas fa-music"></i>
                  </div>
                  <div class="music-info">
                    <div class="music-title">{{ music.title }}</div>
                    <div class="music-artist">{{ music.artist }}</div>
                  </div>
                </div>
              </div>
              <div class="upload-area" id="upload-area" @click="onUploadClick" v-show="!selectedMusic">
                <div class="upload-icon">
                  <i class="fas fa-cloud-upload-alt" v-if="!uploadedFile"></i>
                  <i class="fas fa-check-circle" style="color: var(--success);" v-else></i>
                </div>
                <div class="upload-text" v-if="!uploadedFile">点击上传您的音乐</div>
                <div class="upload-text" v-else>{{ uploadedFile.name }}</div>
                <div class="upload-hint" v-if="!uploadedFile">支持MP3、WAV格式，最大10MB</div>
                <div class="upload-hint" v-else>{{ (uploadedFile.size / 1024 / 1024).toFixed(2) }} MB</div>
                <input type="file" id="music-upload" accept="audio/*" style="display: none;" @change="onFileChange">
              </div>
            </div>
            <div class="form-group">
              <label class="form-label">情绪标签</label>
              <div class="emotion-selector">
                <div
                  v-for="tag in emotionTags"
                  :key="tag.key"
                  class="emotion-tag"
                  :class="{ selected: selectedEmotions.includes(tag.key) }"
                  @click="toggleEmotion(tag.key)"
                >
                  {{ tag.label }}
                </div>
              </div>
            </div>
            <div class="form-actions">
              <button type="button" class="btn btn-secondary" id="draft-button" @click="onDraft">
                保存草稿
              </button>
              <button type="submit" class="btn btn-primary btn-lg" id="post-submit" :disabled="isSubmitting">
                <span v-if="!isSubmitting">发布帖子</span>
                <span v-else><span class="loader"></span> 发布中...</span>
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
    <button id="mobile-menu-button" @click="openSidebar" v-show="isMobile">
      <i class="fas fa-bars"></i>
    </button>
    <div v-if="showToast" :class="['toast', `toast-${toastType}` , showToast ? 'show' : '']">
      <div class="toast-icon">
        <i class="fas" :class="toastType === 'success' ? 'fa-check-circle' : 'fa-exclamation-circle'"></i>
      </div>
      <div class="toast-message">{{ toastMsg }}</div>
      <button class="toast-close" @click="showToast = false">
        <i class="fas fa-times"></i>
      </button>
    </div>
  </div>
</template>

<style>
:root {
  --primary: #2c7be5;
  --primary-dark: #1a56db;
  --gray-light: #edf2f7;
  --gray: #718096;
  --gray-dark: #2d3748;
  --danger: #e53e3e;
  --success: #38a169;
}
body {
  font-family: 'Inter', sans-serif;
  background: linear-gradient(135deg, #e9f6ff 0%, #f7f9fc 100%);
  color: #333;
  margin: 0;
  padding: 0;
  min-height: 100vh;
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
  color: var(--primary);
  letter-spacing: -0.05rem;
  transition: color 0.3s ease;
  cursor: pointer;
}
#logo:hover {
  color: var(--primary-dark);
}
#header-description {
  font-size: 1rem;
  color: var(--gray);
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
  color: var(--gray);
  text-decoration: none;
  font-size: 1rem;
  display: flex;
  align-items: center;
  gap: 0.5rem;
  transition: color 0.3s ease;
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
  background: rgba(255, 255, 255, 0.9);
  box-shadow: 4px 0 20px rgba(0, 0, 0, 0.05);
  padding: 5.5rem 1.5rem 1.5rem;
  overflow-y: auto;
  backdrop-filter: blur(10px);
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
  list-style-type: none;
  padding: 0;
}
#fixed-sidebar ul li {
  margin-bottom: 1rem;
}
#fixed-sidebar ul li a {
  color: var(--gray);
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
  background: var(--gray-light);
  color: var(--primary);
}
#main-content {
  margin-left: 16rem;
  padding: 7rem 3rem 3rem;
  transition: margin-left 0.3s ease;
}
.write-post-container {
  max-width: 800px;
  margin: 0 auto;
  animation: fadeInUp 0.5s ease;
}
@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
.write-post-card {
  background: white;
  border-radius: 1rem;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
  padding: 2rem;
  transition: all 0.3s ease;
}
.write-post-card:hover {
  box-shadow: 0 6px 30px rgba(0, 0, 0, 0.1);
}
.write-post-title {
  font-size: 1.8rem;
  font-weight: 800;
  color: var(--primary);
  margin-bottom: 1.5rem;
  display: flex;
  align-items: center;
  gap: 0.5rem;
}
.form-group {
  margin-bottom: 1.5rem;
}
.form-label {
  display: block;
  font-size: 1rem;
  font-weight: 600;
  color: var(--gray-dark);
  margin-bottom: 0.5rem;
}
.form-control {
  width: 100%;
  padding: 0.75rem 1rem;
  border: 1px solid var(--gray-light);
  border-radius: 0.5rem;
  font-size: 1rem;
  color: var(--gray-dark);
  transition: all 0.3s ease;
}
.form-control:focus {
  outline: none;
  border-color: var(--primary);
  box-shadow: 0 0 0 3px rgba(44, 123, 229, 0.2);
}
.form-textarea {
  min-height: 200px;
  resize: vertical;
}
.btn {
  padding: 0.75rem 1.5rem;
  border-radius: 0.5rem;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  border: none;
}
.btn-primary {
  background: var(--primary);
  color: white;
  box-shadow: 0 4px 10px rgba(44, 123, 229, 0.2);
}
.btn-primary:hover {
  background: var(--primary-dark);
  transform: translateY(-2px);
  box-shadow: 0 6px 15px rgba(44, 123, 229, 0.3);
}
.btn-secondary {
  background: white;
  color: var(--primary);
  border: 1px solid var(--primary);
}
.btn-secondary:hover {
  background: var(--gray-light);
}
.btn-lg {
  padding: 1rem 2rem;
  font-size: 1.1rem;
}
.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
  margin-top: 2rem;
}
.music-selector {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
  margin-top: 1rem;
}
.music-card {
  width: calc(33.333% - 1rem);
  background: white;
  border-radius: 0.5rem;
  padding: 1rem;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  cursor: pointer;
  transition: all 0.3s ease;
  border: 2px solid transparent;
}
@media (max-width: 768px) {
  .music-card {
    width: calc(50% - 0.5rem);
  }
}
.music-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}
.music-card.selected {
  border-color: var(--primary);
  background: rgba(44, 123, 229, 0.05);
}
.music-cover {
  width: 100%;
  aspect-ratio: 1;
  background: var(--gray-light);
  border-radius: 0.25rem;
  margin-bottom: 0.5rem;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--gray);
  font-size: 2rem;
}
.music-info {
  font-size: 0.9rem;
}
.music-title {
  font-weight: 600;
  color: var(--gray-dark);
  margin-bottom: 0.25rem;
}
.music-artist {
  color: var(--gray);
  font-size: 0.8rem;
}
.upload-area {
  border: 2px dashed var(--gray-light);
  border-radius: 0.5rem;
  padding: 2rem;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s ease;
  margin-bottom: 1rem;
}
.upload-area:hover {
  border-color: var(--primary);
  background: rgba(44, 123, 229, 0.05);
}
.upload-icon {
  font-size: 2rem;
  color: var(--gray);
  margin-bottom: 1rem;
}
.upload-text {
  color: var(--gray);
  margin-bottom: 0.5rem;
}
.upload-hint {
  font-size: 0.8rem;
  color: var(--gray);
}
.emotion-selector {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  margin-top: 1rem;
}
.emotion-tag {
  padding: 0.5rem 1rem;
  background: var(--gray-light);
  border-radius: 2rem;
  font-size: 0.9rem;
  cursor: pointer;
  transition: all 0.3s ease;
  border: 1px solid transparent;
}
.emotion-tag:hover {
  background: #e2e8f0;
}
.emotion-tag.selected {
  background: var(--primary);
  color: white;
}
.toast {
  position: fixed;
  bottom: 2rem;
  right: 2rem;
  background: white;
  color: var(--gray-dark);
  padding: 1rem;
  border-radius: 0.5rem;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: center;
  gap: 0.5rem;
  z-index: 1200;
  opacity: 0;
  transform: translateY(20px);
  transition: all 0.3s ease;
}
.toast.show {
  opacity: 1;
  transform: translateY(0);
}
.toast-icon {
  font-size: 1.2rem;
}
.toast-success .toast-icon {
  color: var(--success);
}
.toast-error .toast-icon {
  color: var(--danger);
}
.toast-close {
  background: none;
  border: none;
  color: var(--gray);
  cursor: pointer;
  margin-left: 1rem;
}
.loader {
  border: 2px solid var(--gray-light);
  border-top: 2px solid var(--primary);
  border-radius: 50%;
  width: 16px;
  height: 16px;
  animation: spin 1s linear infinite;
  display: inline-block;
  vertical-align: middle;
  margin-right: 0.5rem;
}
@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}
#mobile-menu-button {
  display: none;
  position: fixed;
  bottom: 2rem;
  right: 2rem;
  width: 56px;
  height: 56px;
  border-radius: 50%;
  background: var(--primary);
  color: white;
  border: none;
  box-shadow: 0 4px 15px rgba(44, 123, 229, 0.3);
  z-index: 1000;
  justify-content: center;
  align-items: center;
  font-size: 1.5rem;
  cursor: pointer;
}
@media (max-width: 768px) {
  #mobile-menu-button {
    display: flex;
  }
}
#editor {
  height: 300px;
  margin-bottom: 1rem;
}
.ql-toolbar {
  border-radius: 0.5rem 0.5rem 0 0;
  border-color: var(--gray-light) !important;
}
.ql-container {
  border-radius: 0 0 0.5rem 0.5rem;
  border-color: var(--gray-light) !important;
  font-family: 'Inter', sans-serif;
}
.character-count {
  text-align: right;
  font-size: 0.8rem;
  color: var(--gray);
  margin-top: 0.5rem;
}
.character-count.warning {
  color: var(--danger);
}
</style>
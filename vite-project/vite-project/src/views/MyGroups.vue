<script setup>
import { ref, reactive, computed, onMounted, nextTick } from 'vue';

const sidebarOpen = ref(false);
const showModal = ref(false);
const groupList = ref([
  {
    icon: 'fa-music',
    title: '放松音乐爱好者',
    description: '分享各种放松心情的音乐，帮助缓解压力和焦虑',
    members: ['A', 'B', 'C', '+9'],
    memberCount: 12,
    lastActive: '2小时前活跃',
  },
  {
    icon: 'fa-spa',
    title: '冥想音乐小组',
    description: '专注于冥想和正念音乐的分享与讨论',
    members: ['D', 'E', 'F', '+5'],
    memberCount: 8,
    lastActive: '昨天活跃',
  },
  {
    icon: 'fa-moon',
    title: '睡眠音乐分享',
    description: '分享有助于睡眠的音乐和声音疗法',
    members: ['G', 'H', 'I', '+12'],
    memberCount: 15,
    lastActive: '3天前活跃',
  },
]);
const searchTerm = ref('');
const newGroup = reactive({
  name: '',
  description: '',
  members: '',
});
const loading = ref(false);

const filteredGroups = computed(() => {
  if (!searchTerm.value) return groupList.value;
  return groupList.value.filter(group => {
    const membersText = group.members.join(',');
    return (
      group.title.includes(searchTerm.value) ||
      group.description.includes(searchTerm.value) ||
      membersText.includes(searchTerm.value)
    );
  });
});

function openSidebar() {
  sidebarOpen.value = !sidebarOpen.value;
}
function openModal() {
  showModal.value = true;
}
function closeModal() {
  showModal.value = false;
  newGroup.name = '';
  newGroup.description = '';
  newGroup.members = '';
}
function saveGroup() {
  if (!newGroup.name.trim()) {
    alert('请填写分组名称');
    return;
  }
  loading.value = true;
  setTimeout(() => {
    const membersArr = newGroup.members
      ? newGroup.members.split(',').map(m => m.trim()).filter(Boolean)
      : [];
    groupList.value.unshift({
      icon: 'fa-users',
      title: newGroup.name,
      description: newGroup.description || '暂无描述',
      members: ['你', ...membersArr.map(m => m.charAt(0))],
      memberCount: 1 + membersArr.length,
      lastActive: '刚刚创建',
    });
    closeModal();
    loading.value = false;
  }, 1500);
}
function deleteGroup(idx) {
  if (!confirm('确定要删除这个分组吗？')) return;
  groupList.value.splice(idx, 1);
}
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
    <div id="fixed-sidebar" :style="sidebarOpen ? 'transform: translateX(0)' : ''">
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
          <input type="text" class="search-input" v-model="searchTerm" placeholder="搜索分组名称或成员...">
          <button class="search-button">
            <i class="fas fa-search"></i>
          </button>
        </div>
        <button class="primary-button" @click="openModal">
          <i class="fas fa-plus"></i> 创建新分组
        </button>
      </div>
      <div v-if="filteredGroups.length" id="group-list" class="groups-grid">
        <div class="group-card" v-for="(group, idx) in filteredGroups" :key="group.title">
          <div class="group-cover">
            <i class="fas" :class="group.icon"></i>
          </div>
          <div class="group-info">
            <h3 class="group-title">{{ group.title }}</h3>
            <p class="group-description">{{ group.description }}</p>
            <div class="group-meta">
              <span><i class="fas fa-users"></i> {{ group.memberCount }} 成员</span>
              <span><i class="fas fa-clock"></i> {{ group.lastActive }}</span>
            </div>
            <div class="group-members">
              <div class="member-avatar" v-for="(m, i) in group.members" :key="i">{{ m }}</div>
            </div>
            <div class="group-actions">
              <button class="secondary-button">进入分组</button>
              <button class="danger-button" @click="deleteGroup(idx)">
                <i class="fas fa-trash"></i>
              </button>
            </div>
          </div>
        </div>
      </div>
      <div v-else class="empty-state">
        <div class="empty-state-icon">
          <i class="fas fa-users-slash"></i>
        </div>
        <h3>暂无分组</h3>
        <p>点击"创建新分组"按钮开始你的第一个分组</p>
      </div>
      <div v-if="showModal" id="create-group-modal" style="display: flex;">
        <div class="modal-content">
          <div class="modal-header">
            <h3 class="modal-title">创建新分组</h3>
            <button class="close-button" @click="closeModal">
              <i class="fas fa-times"></i>
            </button>
          </div>
          <div class="modal-body">
            <div class="form-group">
              <label for="new-group-name" class="form-label">分组名称</label>
              <input type="text" id="new-group-name" class="form-input" v-model="newGroup.name" placeholder="例如：古典音乐爱好者">
            </div>
            <div class="form-group">
              <label for="new-group-description" class="form-label">分组描述</label>
              <input type="text" id="new-group-description" class="form-input" v-model="newGroup.description" placeholder="简单描述分组的目的和主题">
            </div>
            <div class="form-group">
              <label for="new-group-members" class="form-label">邀请成员</label>
              <input type="text" id="new-group-members" class="form-input" v-model="newGroup.members" placeholder="输入用户名，用逗号分隔">
            </div>
          </div>
          <div class="modal-footer">
            <button class="secondary-button" @click="closeModal">取消</button>
            <button class="primary-button" @click="saveGroup" :disabled="loading">
              <span v-show="!loading">创建分组</span>
              <span v-show="loading" class="loading"></span>
            </button>
          </div>
        </div>
      </div>
    </div>
    <button id="mobile-menu-button" @click="openSidebar">
      <i class="fas fa-bars"></i>
    </button>
  </div>
</template>

<style>
:root {
  --primary: #3b82f6;
  --primary-light: #93c5fd;
  --primary-dark: #1d4ed8;
  --gray-light: #f3f4f6;
  --gray: #6b7280;
  --gray-dark: #374151;
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
  display: flex;
  gap: 1rem;
  margin-bottom: 2rem;
  flex-wrap: wrap;
}
.search-box {
  position: relative;
  flex-grow: 1;
  min-width: 300px;
}
.search-input {
  width: 100%;
  padding: 0.75rem 1rem;
  border: 1px solid var(--gray-light);
  border-radius: 0.5rem;
  font-size: 1rem;
  padding-right: 3rem;
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
  background: transparent;
  border: none;
  color: var(--gray);
  cursor: pointer;
}
.primary-button {
  background: var(--primary);
  color: white;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 0.5rem;
  cursor: pointer;
  font-weight: 500;
  transition: all 0.2s ease;
  display: flex;
  align-items: center;
  gap: 0.5rem;
}
.primary-button:hover {
  background: var(--primary-dark);
  transform: translateY(-1px);
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}
.primary-button:active {
  transform: translateY(0);
  box-shadow: none;
}
.groups-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 1.5rem;
  margin-top: 2rem;
}
.group-card {
  background: white;
  border-radius: 0.75rem;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  transition: all 0.2s ease;
}
.group-card:hover {
  box-shadow: 0 10px 15px rgba(0, 0, 0, 0.1);
  transform: translateY(-3px);
}
.group-cover {
  height: 120px;
  background: linear-gradient(135deg, #93c5fd 0%, #3b82f6 100%);
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 2rem;
}
.group-info {
  padding: 1.25rem;
}
.group-title {
  font-size: 1.125rem;
  font-weight: 600;
  margin-bottom: 0.5rem;
  color: var(--gray-dark);
}
.group-description {
  color: var(--gray);
  font-size: 0.875rem;
  margin-bottom: 1rem;
  line-height: 1.5;
}
.group-meta {
  display: flex;
  justify-content: space-between;
  font-size: 0.75rem;
  color: var(--gray);
  margin-bottom: 1rem;
}
.group-members {
  display: flex;
  align-items: center;
  margin-bottom: 1.25rem;
}
.member-avatar {
  width: 2rem;
  height: 2rem;
  border-radius: 50%;
  background: var(--gray-light);
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: -0.5rem;
  border: 2px solid white;
  color: var(--gray);
  font-size: 0.75rem;
}
.member-count {
  margin-left: 1rem;
  font-size: 0.75rem;
  color: var(--gray);
}
.group-actions {
  display: flex;
  gap: 0.75rem;
}
.secondary-button {
  background: white;
  color: var(--primary);
  border: 1px solid var(--primary-light);
  padding: 0.5rem 1rem;
  border-radius: 0.5rem;
  cursor: pointer;
  font-size: 0.875rem;
  transition: all 0.2s ease;
  flex-grow: 1;
  text-align: center;
}
.secondary-button:hover {
  background: var(--primary-light);
  color: var(--primary-dark);
}
.danger-button {
  background: white;
  color: #ef4444;
  border: 1px solid #fca5a5;
  padding: 0.5rem 1rem;
  border-radius: 0.5rem;
  cursor: pointer;
  font-size: 0.875rem;
  transition: all 0.2s ease;
}
.danger-button:hover {
  background: #fee2e2;
  color: #dc2626;
}
#create-group-modal {
  display: none;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  z-index: 1100;
  justify-content: center;
  align-items: center;
  animation: fadeIn 0.3s ease;
}
@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}
.modal-content {
  background: white;
  border-radius: 0.75rem;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
  width: 90%;
  max-width: 500px;
  max-height: 90vh;
  overflow-y: auto;
  animation: slideInUp 0.3s ease;
}
@keyframes slideInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
.modal-header {
  padding: 1.5rem;
  border-bottom: 1px solid var(--gray-light);
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.modal-title {
  font-size: 1.25rem;
  font-weight: 600;
  color: var(--gray-dark);
}
.close-button {
  background: none;
  border: none;
  font-size: 1.5rem;
  color: var(--gray);
  cursor: pointer;
  transition: color 0.2s ease;
}
.close-button:hover {
  color: var(--primary);
}
.modal-body {
  padding: 1.5rem;
}
.form-group {
  margin-bottom: 1.5rem;
}
.form-label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: 500;
  color: var(--gray-dark);
}
.form-input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid var(--gray-light);
  border-radius: 0.5rem;
  font-size: 1rem;
}
.form-input:focus {
  outline: none;
  border-color: var(--primary);
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}
.modal-footer {
  padding: 1.5rem;
  border-top: 1px solid var(--gray-light);
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
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
.loading {
  display: inline-block;
  width: 1rem;
  height: 1rem;
  border: 2px solid rgba(255, 255, 255, 0.3);
  border-radius: 50%;
  border-top-color: white;
  animation: spin 1s ease-in-out infinite;
}
@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}
.empty-state {
  text-align: center;
  padding: 3rem;
  color: var(--gray);
}
.empty-state-icon {
  font-size: 3rem;
  color: var(--gray-light);
  margin-bottom: 1rem;
}
</style>
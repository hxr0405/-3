<script>
export default {
  name: 'Settings',
  data() {
    return {
      nickname: '',
      email: '',
      password: '',
      notifyLike: false,
      notifyComment: false,
      notifyFollow: false,
      theme: 'light',
      showToast: false,
      toastMessage: '',
      notifyNewPost: false,
      profileVisibility: '',
      hidePosts: false,
      hideLikes: false,
      hideFollowers: false,
      allowSearch: false,
      allowMessage: false,
      showDeleteConfirm: false
    }
  },
  computed: {
    showPrivateSub() {
      return this.profileVisibility === 'private';
    },
    showPublicSub() {
      return this.profileVisibility === 'public';
    }
  },
  methods: {
    goToProfile() {
      this.$router.push('/personal-page');
    },
    saveAccount() {
      this.showToastMessage('账户信息已保存');
    },
    showToastMessage(msg) {
      this.toastMessage = msg;
      this.showToast = true;
      setTimeout(() => { this.showToast = false; }, 2000);
    },
    showToastMsg(msg) {
      this.toastMessage = msg;
      this.showToast = true;
      setTimeout(() => { this.showToast = false; }, 2000);
    },
    confirmDeleteAccount() {
      this.showDeleteConfirm = true;
    },
    doDeleteAccount() {
      this.showDeleteConfirm = false;
      this.showToastMsg('账户删除成功。');
    },
    cancelDelete() {
      this.showDeleteConfirm = false;
    }
  }
}
</script>

<template>
  <div class="font-sans text-gray-800">
    <header class="header">
      <h1><i class="fas fa-cog mr-3"></i>心灵花园 - 设置</h1>
      <p>个性化您的使用体验</p>
      <button class="exit-btn" @click="goToProfile">
        <i class="fas fa-arrow-left"></i>
      </button>
    </header>
    <main class="main-content">

      <div class="card">
        <div class="card-header">
          <h2><i class="fas fa-bell mr-3 text-blue-morandi-500"></i>通知设置</h2>
        </div>
        <div class="card-body">
          <div class="setting-item">
            <input type="checkbox" id="newPostNotification" v-model="notifyNewPost">
            <label for="newPostNotification">新帖子通知</label>
          </div>
          <div class="setting-item">
            <input type="checkbox" id="commentNotification" v-model="notifyComment">
            <label for="commentNotification">评论通知</label>
          </div>
          <div class="setting-item">
            <input type="checkbox" id="likeNotification" v-model="notifyLike">
            <label for="likeNotification">点赞通知</label>
          </div>
        </div>
      </div>
      <div class="card">
        <div class="card-header">
          <h2><i class="fas fa-user-shield mr-3"></i>隐私设置</h2>
        </div>
        <div class="card-body">
          <div class="setting-item">
            <input type="radio" id="privateProfile" value="private" v-model="profileVisibility">
            <label for="privateProfile">私人资料</label>
          </div>
          
          <div class="setting-item">
            <input type="radio" id="publicProfile" value="public" v-model="profileVisibility">
            <label for="publicProfile">公开资料</label>
          </div>
          <div class="sub-setting" v-show="showPublicSub">
            <div class="setting-item">
              <input type="checkbox" id="allowSearch" v-model="allowSearch">
              <label for="allowSearch">允许他人搜索到我</label>
            </div>
            <div class="setting-item">
              <input type="checkbox" id="allowMessage" v-model="allowMessage">
              <label for="allowMessage">允许他人给我发消息</label>
            </div>
          </div>
        </div>
      </div>
      <div class="card">
        <div class="card-header">
          <h2><i class="fas fa-trash-alt mr-3"></i>账户管理</h2>
        </div>
        <div class="card-body">
          <button class="button-secondary mb-3">修改密码</button>
          <button class="button-secondary" @click="confirmDeleteAccount">删除账户</button>
        </div>
      </div>
    </main>
    <div class="toast" :class="{ show: showToast }">{{ toastMessage }}</div>
    <div v-if="showDeleteConfirm" class="modal-confirm">
      <div class="modal-content">
        <h3>确定要删除您的账户吗？此操作无法撤销。</h3>
        <div class="modal-actions">
          <button class="button-secondary" @click="doDeleteAccount">确认删除</button>
          <button class="button-primary" @click="cancelDelete">取消</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
body {
  background-color: #e1f4f9;
  min-height: 100vh;
  transition: all 0.3s ease;
  font-family: 'Noto Sans SC', sans-serif;
}
.header {
  background: linear-gradient(135deg, #84bfd9 100%);
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
  padding: 2rem;
  color: white;
  text-align: center;
  margin-bottom: 2rem;
  position: relative;
}
.header h1 {
  font-size: 2.25rem;
  font-weight: bold;
  font-family: 'Noto Serif SC', serif;
}
.header p {
  opacity: 0.9;
  margin-top: 0.5rem;
}
.exit-btn {
  position: absolute;
  top: 1rem;
  right: 1rem;
  background-color: #ff7a7a;
  color: white;
  text-align: center;
  padding: 0.625rem;
  border: none;
  border-radius: 0.3125rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
}
.exit-btn:hover {
  background-color: #ff5555;
}
.main-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
}
.card {
  background-color: white;
  border-radius: 0.75rem;
  box-shadow: 0 4px 14px 0 rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;
  border: 1px solid rgba(0, 0, 0, 0.05);
  margin-bottom: 2rem;
}
.card:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
}
.card-header {
  border-bottom: 1px solid rgba(0, 0, 0, 0.05);
  background-color: rgba(249, 249, 249, 0.8);
  padding: 1.5rem;
}
.card-header h2 {
  font-size: 1.5rem;
  font-weight: bold;
  font-family: 'Noto Serif SC', serif;
  display: flex;
  align-items: center;
}
.card-header h2 i {
  margin-right: 0.75rem;
  color: #75cbeb;
}
.card-body {
  padding: 1.5rem;
}
.button-primary {
  background: linear-gradient(135deg, #75cbeb 0%, #43add4 100%);
  color: white;
  transition: all 0.3s ease;
  box-shadow: 0 2px 6px rgba(191, 122, 77, 0.3);
  padding: 0.75rem 1.5rem;
  border-radius: 0.375rem;
  font-weight: 500;
  cursor: pointer;
}
.button-primary:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(191, 122, 77, 0.4);
  background: linear-gradient(135deg, #c8956b 0%, #b16643 100%);
}
.button-secondary {
  background: linear-gradient(135deg, #8e7f5d 0%, #625642 100%);
  color: white;
  transition: all 0.3s ease;
  box-shadow: 0 2px 6px rgba(142, 127, 93, 0.3);
  padding: 0.75rem 1.5rem;
  border-radius: 0.375rem;
  font-weight: 500;
  cursor: pointer;
}
.button-secondary:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(142, 127, 93, 0.4);
  background: linear-gradient(135deg, #a79a7a 0%, #7a6b4f 100%);
}
.setting-item {
  display: flex;
  align-items: center;
  padding: 0.75rem 0;
  transition: background-color 0.3s ease;
}
.setting-item:hover {
  background-color: #f4f4f4;
}
.setting-item input[type="checkbox"],
.setting-item input[type="radio"] {
  appearance: none;
  -webkit-appearance: none;
  -moz-appearance: none;
  width: 1.25rem;
  height: 1.25rem;
  border: 2px solid #75cbeb;
  border-radius: 0.25rem;
  margin-right: 1rem;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
}
.setting-item input[type="checkbox"]:checked,
.setting-item input[type="radio"]:checked {
  background-color: #75cbeb;
  border-color: #75cbeb;
}
.setting-item input[type="checkbox"]:checked::before,
.setting-item input[type="radio"]:checked::before {
  content: '\f00c';
  font-family: 'Font Awesome 6 Free';
  font-weight: 900;
  color: white;
  font-size: 0.875rem;
}
.setting-item input[type="radio"] {
  border-radius: 50%;
}
.sub-setting {
  margin-left: 2.25rem;
  padding: 0.5rem 0;
  display: block;
}
.modal-confirm {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background: rgba(0,0,0,0.3);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 2000;
}
.modal-content {
  background: #fff;
  border-radius: 1rem;
  padding: 2rem 2.5rem;
  box-shadow: 0 8px 32px rgba(0,0,0,0.18);
  min-width: 320px;
  max-width: 90vw;
}
.modal-actions {
  display: flex;
  justify-content: flex-end;
  gap: 1.5rem;
  margin-top: 2rem;
}
.toast {
  position: fixed;
  bottom: 30px;
  left: 50%;
  transform: translateX(-50%);
  background-color: rgba(0, 0, 0, 0.85);
  color: white;
  padding: 12px 24px;
  border-radius: 8px;
  opacity: 0;
  transition: opacity 0.3s ease-in-out;
  z-index: 1000;
  backdrop-filter: blur(5px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}
.toast.show {
  opacity: 1;
}
</style>
<script>
export default {
  name: 'PersonalPage',
  data() {
    return {
      userData: {
        nickname: '心灵探索者',
        gender: '男',
        bio: '探索心灵深处，遇见更好的自己',
        region: '北京'
      },
      showEditModal: false,
      showCommentModal: false,
      commentContent: ''
    };
  },
  methods: {
    // 更换头像功能
    handleAvatarChange(e) {
      if (e.target.files && e.target.files[0]) {
        const file = e.target.files[0];
        const isImage = file.type.match('image.*');
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isImage) {
          alert('请上传图片文件(JPG/PNG)');
          return;
        }
        if (!isLt2M) {
          alert('图片大小不能超过2MB');
          return;
        }

        const reader = new FileReader();
        reader.onload = (event) => {
          document.getElementById('userAvatar').src = event.target.result;
          console.log('头像已更新:', file.name);
        };
        reader.readAsDataURL(file);
      }
    },
    
    // 编辑资料功能
    openEditModal() {
      this.showEditModal = true;
    },
    
    cancelEdit() {
      this.showEditModal = false;
    },
    
    saveProfile() {
      this.showEditModal = false;
      console.log('资料已更新:', this.userData);
    },
    
    // 点赞功能
    toggleLike(event) {
      const btn = event.currentTarget;
      const isLiked = btn.getAttribute('data-liked') === 'true';
      const likeText = btn.querySelector('.like-text');

      if (isLiked) {
        btn.setAttribute('data-liked', 'false');
        likeText.textContent = '点赞';
        btn.classList.remove('active');
        console.log('取消点赞');
      } else {
        btn.setAttribute('data-liked', 'true');
        likeText.textContent = '已赞';
        btn.classList.add('active');
        console.log('点赞成功');
      }
    },
    
    // 评论功能
    openCommentModal() {
      this.showCommentModal = true;
      this.$nextTick(() => {
        document.getElementById('commentContent').focus();
      });
    },
    
    cancelComment() {
      this.showCommentModal = false;
      this.commentContent = '';
    },
    
    submitComment(e) {
      e.preventDefault();
      if (this.commentContent.trim()) {
        const commentsSection = document.querySelector('.comments-section');
        
        const commentElement = document.createElement('div');
        commentElement.className = 'comment';
        commentElement.innerHTML = `
          <div class="comment-header">
            <img src="https://picsum.photos/40/40" class="comment-avatar">
            <span class="comment-author">${this.userData.nickname}</span>
          </div>
          <p class="comment-content">${this.commentContent}</p>
        `;
        commentsSection.appendChild(commentElement);

        console.log('评论已发布:', this.commentContent);
        this.showCommentModal = false;
        this.commentContent = '';
      } else {
        alert('评论内容不能为空');
      }
    },
    
    // 删除功能
    deletePost(event) {
      if (confirm('确定要删除这条内容吗？')) {
        const postItem = event.currentTarget.closest('.post-item');
        console.log('内容已删除');
        postItem.remove();
      }
    },
    
    // 点击模态框外部关闭模态框
    closeModalOnOutsideClick(event) {
      if (event.target.classList.contains('modal')) {
        if (event.target === document.getElementById('editModal')) {
          this.showEditModal = false;
        }
        if (event.target === document.getElementById('commentModal')) {
          this.showCommentModal = false;
          this.commentContent = '';
        }
      }
    }
  },
  mounted() {
    // 添加点击事件监听器
    window.addEventListener('click', this.closeModalOnOutsideClick);
  },
  beforeUnmount() {
    // 移除事件监听器
    window.removeEventListener('click', this.closeModalOnOutsideClick);
  }
}
</script>

<template>
  <div>
    <header class="header">
      <div class="header-title" @click="$router.push('/')">
        <i class="fas fa-leaf"></i>
        <h1>心灵花园</h1>
      </div>
      <button class="exit-btn" @click="$router.push('/')">
        <i class="fas fa-sign-out-alt"></i>
      </button>
    </header>

    <div class="container" style="max-width: 1200px; margin: 0 auto; padding: 0 2rem;">
      <div class="profile-section">
        <div class="avatar-section">
          <img src="https://picsum.photos/200/200" alt="头像" class="avatar" id="userAvatar">
          <input type="file" id="avatarInput" ref="avatarInput" accept="image/*" style="display: none;" @change="handleAvatarChange">
          <button class="change-avatar-btn" @click="$refs.avatarInput.click()">
            <i class="fas fa-camera"></i> 更换头像
          </button>
        </div>

        <div class="user-data">
          <span><strong>昵称:</strong> {{ userData.nickname }}</span>
          <span><strong>性别:</strong> {{ userData.gender }}</span>
          <span><strong>个性签名:</strong> {{ userData.bio }}</span>
          <span><strong>地区:</strong> {{ userData.region }}</span>
          <span><strong>粉丝:</strong> 100</span>
        </div>

        <div class="edit-button-container">
          <button class="edit-button" @click="openEditModal">
            <i class="fas fa-pen"></i> 编辑资料
          </button>
        </div>
      </div>

      <div class="main-content" style="display: grid; grid-template-columns: 1fr 2fr; gap: 3rem;">
        <div class="function-column">
          <h2 class="function-title">社交互动</h2>
          <button class="nav-button" @click="$router.push('/my-collections')"><i class="fas fa-thumbs-up"></i>我点赞的</button>
          <button class="nav-button" @click="$router.push('/my-favorites')"><i class="fas fa-bookmark"></i>我收藏的</button>
          <button class="nav-button" @click="$router.push('/message-center')"><i class="fas fa-comment"></i>消息中心</button>

          <h2 class="function-title">音乐治疗</h2>
          <button class="nav-button" @click="$router.push('/my-playlists')"><i class="fas fa-music"></i>我的歌单</button>
          <button class="nav-button" @click="$router.push('/my-likes')"><i class="fas fa-heart"></i>我喜欢的</button>

          <h2 class="function-title">其他功能</h2>
          <button class="nav-button" @click="$router.push('/mood-calendar')"><i class="fas fa-clipboard-list"></i>心情日历</button>
          <button class="nav-button" @click="$router.push('/psychological-diary')"><i class="fas fa-book"></i>心理日记</button>
          <button class="nav-button" @click="$router.push('/psych-test')"><i class="fas fa-clipboard-list"></i>心理测评</button>
          <button class="nav-button" @click="$router.push('/settings')"><i class="fas fa-cog"></i>设置</button>
        </div>

        <div class="post-column">
          <h2 class="post-title">我发布的</h2>
          <div class="post-item">
            <h3>今天的心情日记</h3>
            <p>记录一段关于治愈瞬间的文字...</p>
            <div class="post-actions">
              <button class="action-button like-btn" data-liked="false" @click="toggleLike">
                <i class="fas fa-thumbs-up"></i> <span class="like-text">点赞</span>
              </button>
              <button class="action-button" id="commentBtn" style="background: linear-gradient(135deg, #4ba3d6, #3b82f6);" @click="openCommentModal">
                <i class="fas fa-comment"></i> 评论
              </button>
              <button class="action-button delete-btn" @click="deletePost">
                <i class="fas fa-trash"></i> 删除
              </button>
            </div>
            <div class="comments-section"></div>
          </div>
        </div>
      </div>
    </div>

    <!-- 编辑资料模态框 -->
    <div class="modal" id="editModal" v-show="showEditModal">
      <div class="modal-content">
        <h3 class="modal-title">编辑个人资料</h3>
        <form @submit.prevent="saveProfile">
          <div class="form-group">
            <label for="nickname">昵称</label>
            <input type="text" id="nickname" v-model="userData.nickname">
          </div>
          <div class="form-group">
            <label for="gender">性别</label>
            <input type="text" id="gender" v-model="userData.gender">
          </div>
          <div class="form-group">
            <label for="bio">个性签名</label>
            <textarea id="bio" v-model="userData.bio"></textarea>
          </div>
          <div class="form-group">
            <label for="region">地区</label>
            <input type="text" id="region" v-model="userData.region">
          </div>
          <div class="modal-actions">
            <button type="button" class="modal-button modal-cancel" @click="cancelEdit">取消</button>
            <button type="submit" class="modal-button modal-submit">保存</button>
          </div>
        </form>
      </div>
    </div>

    <!-- 评论模态框 -->
    <div class="modal" id="commentModal" v-show="showCommentModal">
      <div class="modal-content">
        <h3 class="modal-title">发表评论</h3>
        <form @submit.prevent="submitComment">
          <div class="form-group">
            <textarea id="commentContent" placeholder="写下你的评论..." v-model="commentContent"></textarea>
          </div>
          <div class="modal-actions">
            <button type="button" class="modal-button modal-cancel" @click="cancelComment">取消</button>
            <button type="submit" class="modal-button modal-submit">发布</button>
          </div>
        </form>
      </div>
    </div>

    <!-- 页脚 -->
    <footer>
      <p>&copy; 2025 心灵花园. 保留所有权利。 | <a href="#">隐私政策</a> | <a href="#">使用条款</a></p>
    </footer>
  </div>
</template>

<style scoped>
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

body {
  background: linear-gradient(to bottom, #f5f9fc, #e1f4f9);
  font-family: 'Noto Sans SC', sans-serif;
  line-height: 1.6;
  color: #333;
}

/* 顶部导航栏 */
header {
  background: linear-gradient(135deg, #a1c4d9, #6a91b0);
  box-shadow: 0 4px 16px rgba(121, 166, 199, 0.2);
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1.5rem 2rem;
  margin-bottom: 2rem;
}

.header-title {
  display: flex;
  align-items: center;
  gap: 0.8rem;
  color: white;
  cursor: pointer;
  transition: transform 0.3s ease, color 0.3s ease;
}

.header-title:hover {
  transform: scale(1.05);
  color: #e1f4f9;
}

.header-title i {
  font-size: 1.5rem;
  color: #e1f4f9;
  transition: color 0.3s ease;
}

.header-title:hover i {
  color: #ffffff;
}

.header-title h1 {
  font-family: 'Noto Serif SC', serif;
  font-size: 1.8rem;
  font-weight: 500;
}

.exit-btn {
  background: rgba(255, 255, 255, 0.2);
  border: none;
  border-radius: 50%;
  width: 40px;
  height: 40px;
  font-size: 1.2rem;
  color: white;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: transform 0.3s ease, background 0.3s ease;
}

.exit-btn:hover {
  background: rgba(255, 255, 255, 0.3);
  transform: rotate(15deg);
}

/* 个人信息板块 */
.profile-section {
  background: linear-gradient(to bottom, #ffffff, #f9fcff);
  border-radius: 1.5rem;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.08);
  padding: 2.5rem;
  margin: 0 1rem 2rem;
  display: grid;
  grid-template-columns: auto 1fr auto;
  align-items: center;
  gap: 2rem;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.profile-section:hover {
  transform: translateY(-5px);
  box-shadow: 0 12px 32px rgba(0, 0, 0, 0.12);
}

.avatar-section {
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.avatar {
  width: 140px;
  height: 140px;
  border-radius: 50%;
  border: 5px solid #e1f4f9;
  box-shadow: 0 8px 24px rgba(121, 166, 199, 0.15);
  margin-bottom: 1.2rem;
  object-fit: cover;
  transition: border-color 0.3s ease;
}

.avatar:hover {
  border-color: #79a6c7;
}

.change-avatar-btn {
  background: linear-gradient(135deg, #79a6c7, #6a91b0);
  color: white;
  padding: 0.7rem 1.4rem;
  border-radius: 0.75rem;
  font-size: 0.95rem;
  cursor: pointer;
  border: none;
  box-shadow: 0 4px 12px rgba(121, 166, 199, 0.2);
  display: flex;
  align-items: center;
  gap: 0.5rem;
  transition: background 0.3s ease, transform 0.3s ease;
}

.change-avatar-btn:hover {
  background: linear-gradient(135deg, #6a91b0, #5a7a99);
  transform: translateY(-2px);
}

.user-data {
  display: flex;
  flex-direction: column;
  gap: 1.2rem;
}

.user-data span {
  font-size: 1.15rem;
  color: #4b5e77;
  letter-spacing: 0.02em;
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.user-data span::before {
  font-family: "Font Awesome 6 Free";
  font-weight: 900;
  color: #79a6c7;
  font-size: 1rem;
}

.user-data span:nth-child(1)::before {
  content: "\f007";
}

/* 用户图标 */
.user-data span:nth-child(2)::before {
  content: "\f224";
}

/* 性别图标 */
.user-data span:nth-child(3)::before {
  content: "\f075";
}

/* 个性签名图标 */
.user-data span:nth-child(4)::before {
  content: "\f3c5";
}

/* 地区图标 */
.user-data span:nth-child(5)::before {
  content: "\f004";
}

/* 粉丝图标 */

.user-data strong {
  color: #333;
  font-weight: 600;
}

.edit-button {
  background: linear-gradient(135deg, #79a6c7, #6a91b0);
  color: white;
  padding: 0.8rem 1.6rem;
  border-radius: 0.75rem;
  font-size: 1rem;
  cursor: pointer;
  border: none;
  box-shadow: 0 4px 12px rgba(121, 166, 199, 0.2);
  display: flex;
  align-items: center;
  gap: 0.5rem;
  transition: transform 0.3s ease, background 0.3s ease;
}

.edit-button:hover {
  background: linear-gradient(135deg, #6a91b0, #5a7a99);
  transform: translateY(-2px);
}

.edit-button:active {
  transform: scale(0.95);
}

/* 功能板块 */
.function-column {
  background: #ffffff;
  border-radius: 1.5rem;
  box-shadow: 0 6px 18px rgba(0, 0, 0, 0.05);
  padding: 2rem;
  margin-bottom: 2rem;
}

.function-title {
  font-size: 1.25rem;
  color: #79a6c7;
  padding-bottom: 0.8rem;
  margin-bottom: 1.5rem;
  position: relative;
}

.function-title::after {
  content: "";
  position: absolute;
  bottom: 0;
  left: 0;
  width: 0;
  height: 2px;
  background: linear-gradient(to right, #79a6c7, #e1f4f9);
  transition: width 0.3s ease;
}

.function-title:hover::after {
  width: 100%;
}

.nav-button {
  display: flex;
  align-items: center;
  gap: 0.8rem;
  background: #f5f9fc;
  color: #79a6c7;
  padding: 0.9rem 1.1rem;
  border-radius: 0.6rem;
  font-size: 0.95rem;
  margin-bottom: 0.8rem;
  cursor: pointer;
  transition: all 0.3s ease;
}

.nav-button:hover {
  background: #e1f4f9;
  transform: translateX(5px);
  box-shadow: 0 2px 8px rgba(121, 166, 199, 0.15);
}

.nav-button i {
  font-size: 1.1rem;
  color: #79a6c7;
  transition: transform 0.3s ease;
}

.nav-button:hover i {
  transform: scale(1.2);
}

/* 帖子板块 */
.post-column {
  background: #ffffff;
  border-radius: 1.5rem;
  box-shadow: 0 6px 18px rgba(0, 0, 0, 0.05);
  padding: 2rem;
}

.post-title {
  font-size: 1.4rem;
  background: linear-gradient(to right, #79a6c7, #6a91b0);
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;
  margin-bottom: 1.5rem;
  transition: opacity 0.3s ease;
}

.post-title:hover {
  opacity: 0.8;
}

.post-item {
  padding: 1.5rem;
  margin-bottom: 1.5rem;
  border: 1px solid #f0f8ff;
  border-radius: 0.75rem;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.post-item:hover {
  transform: scale(1.02);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
}

.post-item h3 {
  font-size: 1.2rem;
  color: #333;
  margin-bottom: 0.5rem;
}

.post-item p {
  color: #4b5e77;
  font-size: 0.95rem;
}

.post-actions {
  display: flex;
  gap: 0.8rem;
  margin-top: 1rem;
}

.action-button {
  padding: 0.6rem 1rem;
  border-radius: 0.5rem;
  font-size: 0.9rem;
  cursor: pointer;
  border: none;
  color: white;
  display: flex;
  align-items: center;
  gap: 0.4rem;
  transition: all 0.3s ease;
}

.action-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.action-button.like-btn {
  background: linear-gradient(135deg, #79a6c7, #6a91b0);
}

.action-button.like-btn.active {
  background: linear-gradient(135deg, #6a91b0, #5a7a99);
}

.action-button#commentBtn {
  background: linear-gradient(135deg, #4ba3d6, #3b82f6);
}

.action-button.delete-btn {
  background: linear-gradient(135deg, #ff6b6b, #e55a5a);
}

/* 模态框样式 */
.modal {
  display: flex;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.6);
  z-index: 1000;
  justify-content: center;
  align-items: center;
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
  background: #ffffff;
  padding: 2rem;
  border-radius: 1rem;
  width: 90%;
  max-width: 520px;
  box-shadow: 0 12px 40px rgba(0, 0, 0, 0.2);
  animation: slideInModal 0.3s ease;
}

@keyframes slideInModal {
  from {
    transform: translateY(-20px);
    opacity: 0;
  }

  to {
    transform: translateY(0);
    opacity: 1;
  }
}

.modal-title {
  font-size: 1.4rem;
  color: #79a6c7;
  margin-bottom: 1.2rem;
}

.modal-actions {
  display: flex;
  justify-content: flex-end;
  gap: 0.8rem;
  margin-top: 1.5rem;
}

.modal-button {
  padding: 0.7rem 1.4rem;
  border-radius: 0.5rem;
  cursor: pointer;
  border: none;
  transition: all 0.3s ease;
}

.modal-cancel {
  background: #f5f9fc;
  color: #666;
}

.modal-submit {
  background: linear-gradient(135deg, #79a6c7, #6a91b0);
  color: white;
}

.modal-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.form-group {
  margin-bottom: 1.2rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.4rem;
  color: #4a4a4a;
  font-weight: 500;
}

.form-group input,
.form-group textarea {
  width: 100%;
  padding: 0.7rem;
  border: 1px solid #ddd;
  border-radius: 0.5rem;
  font-family: inherit;
  transition: border-color 0.3s ease, box-shadow 0.3s ease;
}

.form-group input:focus,
.form-group textarea:focus {
  border-color: #79a6c7;
  box-shadow: 0 0 8px rgba(121, 166, 199, 0.2);
  outline: none;
}

.form-group textarea {
  min-height: 90px;
  resize: vertical;
}

/* 评论样式 */
.comments-section {
  margin-top: 1.2rem;
  padding: 1rem;
  background: #f9fbfd;
  border-radius: 0.5rem;
}

.comment {
  padding: 0.8rem;
  background: #ffffff;
  border-radius: 0.5rem;
  margin-bottom: 0.8rem;
  transition: background 0.3s ease;
}

.comment:hover {
  background: #f5f9fc;
}

.comment-header {
  display: flex;
  align-items: center;
  margin-bottom: 0.4rem;
}

.comment-avatar {
  width: 28px;
  height: 28px;
  border-radius: 50%;
  margin-right: 0.7rem;
}

.comment-author {
  font-weight: 500;
  color: #333;
}

.comment-content {
  margin-left: 36px;
  color: #4b5e77;
  font-size: 0.9rem;
}

/* 页脚样式 */
footer {
  background: linear-gradient(to bottom, #e9f6ff, #cbf2fe);
  text-align: center;
  padding: 2rem 1rem;
  margin-top: 2rem;
  box-shadow: 0 -4px 12px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

footer:hover {
  box-shadow: 0 -6px 16px rgba(0, 0, 0, 0.15);
}

footer p {
  color: #4b5e77;
  font-size: 0.9rem;
}

footer a {
  color: #79a6c7;
  text-decoration: none;
  transition: color 0.3s ease;
}

footer a:hover {
  color: #6a91b0;
  text-decoration: underline;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .main-content {
    grid-template-columns: 1fr !important;
  }

  .profile-section {
    grid-template-columns: 1fr;
    text-align: center;
    gap: 1.5rem;
  }

  .avatar {
    width: 120px;
    height: 120px;
  }

  .user-data span {
    font-size: 1rem;
  }

  .header-title h1 {
    font-size: 1.5rem;
  }
}

@media (max-width: 480px) {
  header {
    padding: 1rem;
  }

  .profile-section,
  .function-column,
  .post-column {
    margin: 0 0.5rem 1.5rem;
    padding: 1.5rem;
  }

  .avatar {
    width: 100px;
    height: 100px;
  }

  .change-avatar-btn,
  .edit-button {
    padding: 0.6rem 1.2rem;
    font-size: 0.9rem;
  }

  .post-title,
  .function-title {
    font-size: 1.2rem;
  }

  .nav-button,
  .action-button {
    font-size: 0.85rem;
  }

  footer {
    padding: 1.5rem 0.5rem;
  }

  footer p {
    font-size: 0.85rem;
  }
}
</style>
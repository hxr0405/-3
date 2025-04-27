<script>
export default {
  name: 'MessageCenter',
  data() {
    return {
      likeCollectMessages: [
        { avatar: 'https://picsum.photos/50?1', content: '用户@小太阳 赞了你的帖子', time: '2025-04-19 10:05' },
        { avatar: 'https://picsum.photos/50?2', content: '用户@心灵守护 收藏了你的帖子', time: '2025-04-18 15:22' }
      ],
      commentMessages: [
        { avatar: 'https://picsum.photos/50?3', user: '心灵探索者', time: '2025-04-17 09:30', comment: '很喜欢你的分享，受益匪浅！' },
        { avatar: 'https://picsum.photos/50?4', user: '治愈小天使', time: '2025-04-16 20:10', comment: '这首歌单太棒了，收藏了！' }
      ],
      followMessages: [
        { avatar: 'https://picsum.photos/50?5', user: '新粉丝A', time: '2025-04-15 12:00' },
        { avatar: 'https://picsum.photos/50?6', user: '新粉丝B', time: '2025-04-14 18:45' }
      ]
    }
  },
  methods: {
    goToProfile() {
      this.$router.push('/personal-page');
    },
    viewPost(msg) {
      // 跳转到帖子详情页
      alert('跳转到帖子详情页（示例）');
    },
    followBack(msg) {
      // 回关逻辑
      alert('已回关 ' + msg.user);
    }
  }
}
</script>

<template>
  <div class="font-sans text-gray-800">
    <header class="header">
      <h1><i class="fas fa-bell mr-3"></i>消息中心</h1>
      <p>查看所有互动消息</p>
      <button class="user-btn" title="个人中心" @click="goToProfile">
        <i class="fas fa-user"></i>
      </button>
    </header>
    <main class="main-content">
      <div class="card">
        <div class="card-header">
          <h2><i class="fas fa-thumbs-up mr-3 text-blue-morandi-500"></i>赞和收藏</h2>
          <p>共 <span class="font-bold text-blue-morandi-600">{{ likeCollectMessages.length }}</span> 条消息</p>
        </div>
        <div class="card-body">
          <div v-for="(msg, idx) in likeCollectMessages" :key="'like-' + idx" class="message-item like-collect-item">
            <img :src="msg.avatar" alt="用户头像" class="message-avatar">
            <div class="message-content">
              <h3>{{ msg.content }}</h3>
              <div class="message-meta">{{ msg.time }}</div>
              <div class="message-actions">
                <i class="fas fa-link mr-1"></i><span class="link" @click="viewPost(msg)">查看帖子</span>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="card">
        <div class="card-header">
          <h2><i class="fas fa-comment-dots mr-3 text-blue-morandi-500"></i>收到的评论</h2>
          <p>共 <span class="font-bold text-blue-morandi-600">{{ commentMessages.length }}</span> 条评论</p>
        </div>
        <div class="card-body">
          <div v-for="(msg, idx) in commentMessages" :key="'comment-' + idx" class="message-item received-comment-item">
            <img :src="msg.avatar" alt="用户头像" class="message-avatar">
            <div class="message-content">
              <h3>{{ msg.user }} 评论了你的帖子</h3>
              <div class="message-meta">{{ msg.time }}</div>
              <div class="comment-preview">
                <p>{{ msg.comment }}</p>
              </div>
              <div class="message-actions">
                <i class="fas fa-link mr-1"></i><span class="link" @click="viewPost(msg)">查看帖子</span>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="card">
        <div class="card-header">
          <h2><i class="fas fa-user-plus mr-3 text-green-morandi-500"></i>新粉丝</h2>
          <p>共 <span class="font-bold text-blue-morandi-600">{{ followMessages.length }}</span> 条新关注</p>
        </div>
        <div class="card-body">
          <div v-for="(msg, idx) in followMessages" :key="'follow-' + idx" class="message-item new-follow-item">
            <img :src="msg.avatar" alt="用户头像" class="message-avatar">
            <div class="message-content">
              <h3>{{ msg.user }} 关注了你</h3>
              <div class="message-meta">{{ msg.time }}</div>
              <div class="message-actions">
                <button @click="followBack(msg)">回关</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<style scoped>
body {
  background-color: #e1f4f9;
  font-family: 'Noto Sans SC', sans-serif;
  min-height: 100vh;
  margin: 0;
  padding: 0;
}
.header {
  background: linear-gradient(135deg, #84bfd9 100%);
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
  color: white;
  padding: 2rem;
  text-align: center;
  margin-bottom: 2rem;
  position: relative;
}
.header h1 {
  font-family: 'Noto Serif SC', serif;
  font-size: 2.25rem;
  font-weight: bold;
}
.header p {
  opacity: 0.9;
  margin: 0.5rem 0 0;
  font-size: 1.125rem;
}
.user-btn {
  position: absolute;
  top: 1.5rem;
  right: 1.5rem;
  background-color: rgba(255, 255, 255, 0.2);
  color: white;
  text-align: center;
  padding: 0.75rem;
  border: none;
  border-radius: 50%;
  cursor: pointer;
  transition: all 0.3s ease;
  width: 3rem;
  height: 3rem;
  display: flex;
  align-items: center;
  justify-content: center;
}
.user-btn:hover {
  background-color: rgba(255, 255, 255, 0.3);
  transform: scale(1.05);
}
.user-btn i {
  font-size: 1.5rem;
}
.main-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1.5rem;
}
.card {
  background-color: white;
  border-radius: 0.75rem;
  box-shadow: 0 4px 14px 0 rgba(0, 0, 0, 0.08);
  border: 1px solid rgba(0, 0, 0, 0.05);
  margin-bottom: 2rem;
}
.card-header {
  border-bottom: 1px solid rgba(0, 0, 0, 0.05);
  background-color: rgba(249, 249, 249, 0.8);
  padding: 1.5rem;
}
.card-header h2 {
  font-family: 'Noto Serif SC', serif;
  font-size: 1.5rem;
  font-weight: bold;
  display: flex;
  align-items: center;
}
.card-header h2 i {
  margin-right: 0.75rem;
  color: #75cbeb;
}
.message-item {
  padding: 1.5rem;
  border-bottom: 1px solid #f0f0f0;
  transition: all 0.3s ease;
}
.message-item:last-child {
  border-bottom: none;
}
.message-item:hover {
  background-color: #f8f9fa;
}
.message-avatar {
  width: 3.5rem;
  height: 3.5rem;
  border-radius: 50%;
  margin-right: 1rem;
}
.message-content h3 {
  font-size: 1rem;
  font-weight: 500;
  color: #333;
  margin-bottom: 0.25rem;
}
.message-meta {
  color: #6b7280;
  font-size: 0.875rem;
  margin-bottom: 0.5rem;
}
.message-actions {
  font-size: 0.875rem;
  color: #718096;
  cursor: pointer;
}
.link {
  color: #2c8cc4;
  text-decoration: underline;
  cursor: pointer;
}
.like-collect-item {
  display: flex;
  align-items: center;
}
.like-collect-item i {
  font-size: 1.25rem;
  margin-right: 0.5rem;
  color: #ffd700;
}
.received-comment-item {
  display: flex;
  gap: 1.5rem;
}
.comment-preview {
  flex:-1;
}
.comment-preview p {
  color: #4b5563;
  line-height: 1.6;
  margin: 0.5rem 0;
}
.new-follow-item {
  display: flex;
  align-items: center;
}
.new-follow-item .message-actions button {
  background-color: #2c8cc4;
  color: white;
  padding: 0.375rem 0.75rem;
  border-radius: 0.375rem;
  border: none;
  cursor: pointer;
  transition: background-color 0.3s ease;
}
.new-follow-item .message-actions button:hover {
  background-color: #247ab6;
}
</style>
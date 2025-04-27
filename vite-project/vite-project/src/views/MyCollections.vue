<template>
    <div class="font-sans text-gray-800">
      <header class="header">
        <h1><i class="fas fa-thumbs-up mr-3"></i>我点赞的</h1>
        <p>查看您点赞过的所有内容</p>
        <button class="user-btn" title="个人中心" @click="goToProfile">
          <i class="fas fa-user"></i>
        </button>
      </header>
      
      <main class="main-content">
        <div class="card">
          <div class="card-header">
            <h2><i class="fas fa-heart mr-3 text-blue-morandi-600"></i>点赞内容</h2>
            <p>共 <span class="font-bold text-blue-morandi-600">{{ likedItems.length }}</span> 条点赞记录</p>
          </div>
          
          <div class="card-body">
            <!-- 点赞帖子列表 -->
            <div v-if="likedItems.length > 0">
              <div v-for="(item, index) in likedItems" :key="index" class="liked-item" :style="{ opacity: item.visible ? '1' : '0' }">
                <div class="flex items-start p-4">
                  <img :src="item.avatar" alt="用户头像" class="w-10 h-10 rounded-full">
                  <div class="flex-1">
                    <div class="flex justify-between items-center mb-2">
                      <h3 class="font-semibold text-gray-800">{{ item.username }}</h3>
                      <span class="text-xs text-gray-500">{{ item.date }}</span>
                    </div>
                    <p class="text-gray-700 mb-3">{{ item.content }}</p>
                    <div class="flex items-center text-sm text-gray-500">
                      <span class="mr-4"><i class="fas fa-thumbs-up mr-1 text-blue-morandi-500"></i> 已点赞</span>
                      <span><i class="fas fa-comment mr-1"></i> {{ item.comments }}条评论</span>
                    </div>
                  </div>
                </div>
                <div class="mt-3 pt-3 border-t border-gray-100 flex justify-end p-4">
                  <button class="text-sm px-3 py-1 rounded-md bg-gray-100 text-gray-600 hover:bg-gray-200 mr-2" @click="viewOriginal(item)">
                    <i class="fas fa-comment-dots mr-1"></i>查看原文
                  </button>
                  <button class="text-sm px-3 py-1 rounded-md bg-pink-100 text-pink-600 hover:bg-pink-200" @click="unlikePost(index)">
                    <i class="fas fa-heart mr-1"></i>取消点赞
                  </button>
                </div>
              </div>
            </div>
            
            <!-- 空状态 -->
            <div v-else class="empty-state">
              <i class="far fa-heart"></i>
              <h3>暂无点赞内容</h3>
              <p>当您点赞了内容后，它们会显示在这里</p>
            </div>
          </div>
        </div>
      </main>
    </div>
  </template>
  
  <script>
  export default {
    name: 'MyLikes',
    data() {
      return {
        likedItems: [
          {
            id: 1,
            username: '用户昵称',
            avatar: 'https://picsum.photos/50',
            date: '2025-04-15',
            content: '这是我点赞的帖子内容示例，这里展示了帖子的部分文字内容...',
            comments: 8,
            visible: true
          },
          {
            id: 2,
            username: '另一个用户',
            avatar: 'https://picsum.photos/51',
            date: '2025-04-10',
            content: '心理健康小贴士：每天花10分钟进行正念冥想，可以有效缓解焦虑情绪...',
            comments: 15,
            visible: true
          },
          {
            id: 3,
            username: '心理爱好者',
            avatar: 'https://picsum.photos/52',
            date: '2025-04-05',
            content: '推荐一本关于心理疗愈的好书：《情绪急救》，本书提供了一系列实用的工具和技巧...',
            comments: 23,
            visible: true
          }
        ]
      };
    },
    methods: {
      unlikePost(index) {
        // 设置渐变动画效果
        this.likedItems[index].visible = false;
        
        // 动画结束后移除项目
        setTimeout(() => {
          this.likedItems.splice(index, 1);
          // 在实际应用中，这里应该有API调用来更新后端数据
        }, 300);
      },
      viewOriginal(item) {
        // 实际应用中跳转到帖子详情页
        console.log('查看帖子详情:', item.id);
        // this.$router.push({ name: 'PostDetail', params: { id: item.id } });
      },
      goToProfile() {
        // 跳转到个人页面
        this.$router.push({ name: 'PersonalPage' });
      }
    }
  };
  </script>
  
  <style scoped>
  .header {
    background-color: #fff;
    padding: 1.5rem 2rem;
    text-align: center;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
    position: relative;
    border-bottom: 1px solid #f0f0f0;
  }
  
  .header h1 {
    font-size: 1.5rem;
    font-weight: 600;
    color: #333;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  
  .header h1 i {
    color: #68a4d0;
    margin-right: 0.75rem;
  }
  
  .header p {
    color: #6b7280;
    font-size: 0.875rem;
    margin-top: 0.25rem;
  }
  
  .user-btn {
    position: absolute;
    top: 1.5rem;
    right: 2rem;
    background: none;
    border: none;
    color: #68a4d0;
    font-size: 1.25rem;
    cursor: pointer;
    width: 2.5rem;
    height: 2.5rem;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: all 0.3s ease;
  }
  
  .user-btn:hover {
    background-color: #f3f4f6;
  }
  
  .main-content {
    padding: 2rem;
    max-width: 900px;
    margin: 0 auto;
  }
  
  .card {
    background-color: #fff;
    border-radius: 1rem;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
    overflow: hidden;
  }
  
  .card-header {
    padding: 1.5rem 2rem;
    background-color: #f8f9fa;
    border-bottom: 1px solid #f0f0f0;
  }
  
  .card-header h2 {
    font-size: 1.25rem;
    font-weight: 600;
    color: #333;
    display: flex;
    align-items: center;
  }
  
  .card-header h2 i {
    margin-right: 0.5rem;
  }
  
  .card-header p {
    color: #6b7280;
    font-size: 0.875rem;
    margin-top: 0.25rem;
  }
  
  .card-body {
    padding: 1.5rem 2rem;
  }
  
  .liked-item {
    background-color: #fff;
    border-radius: 0.75rem;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
    border: 1px solid #f0f0f0;
    margin-bottom: 1.5rem;
    transition: all 0.3s ease;
  }
  
  .liked-item:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
  }
  
  .liked-item .flex {
    gap: 1.5rem;
  }
  
  .liked-item img {
    width: 3.75rem;
    height: 3.75rem;
    border-radius: 50%;
    object-fit: cover;
  }
  
  .liked-item h3 {
    font-size: 1.125rem;
    font-weight: 500;
    color: #333;
  }
  
  .liked-item .text-xs {
    color: #6b7280;
    font-size: 0.75rem;
  }
  
  .liked-item p {
    color: #4b5563;
    line-height: 1.6;
    margin: 0.75rem 0;
  }
  
  .liked-item .flex.items-center {
    color: #718096;
    font-size: 0.875rem;
  }
  
  .liked-item .flex.items-center i {
    margin-right: 0.25rem;
    color: #75cbeb;
  }
  
  .liked-item .border-t {
    border-top-color: #e5e7eb;
  }
  
  .liked-item button {
    font-size: 0.875rem;
    padding: 0.5rem 1rem;
    border-radius: 0.375rem;
    transition: all 0.3s ease;
  }
  
  .liked-item .bg-gray-100 {
    background-color: #f3f4f6;
    color: #374151;
  }
  
  .liked-item .bg-gray-100:hover {
    background-color: #e5e7eb;
  }
  
  .liked-item .bg-pink-100 {
    background-color: #fce7f3;
    color: #be185d;
  }
  
  .liked-item .bg-pink-100:hover {
    background-color: #fbcfe8;
  }
  
  .empty-state {
    background-color: #f8f7f4;
    border: 2px dashed #e8d5c2;
    border-radius: 0.75rem;
    padding: 2rem;
    text-align: center;
    margin: 2rem 0;
  }
  
  .empty-state i {
    font-size: 3rem;
    color: #cbd5e0;
    margin-bottom: 1rem;
  }
  
  .empty-state h3 {
    font-size: 1.25rem;
    font-weight: 500;
    color: #718096;
  }
  
  .empty-state p {
    color: #a0aec0;
    margin-top: 0.5rem;
  }
  
  /* 添加响应式布局 */
  @media (max-width: 768px) {
    .header, .main-content {
      padding: 1rem;
    }
    
    .card-header, .card-body {
      padding: 1rem;
    }
    
    .liked-item .flex {
      flex-direction: column;
      gap: 0.75rem;
    }
    
    .liked-item img {
      width: 3rem;
      height: 3rem;
    }
  }
  </style> 
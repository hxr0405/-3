<template>
  <div class="font-sans text-gray-800">
    <header class="header">
      <h1><i class="fas fa-bookmark mr-3"></i>我收藏的</h1>
      <p>查看您收藏的所有内容</p>
      <button class="user-btn" title="个人中心">
        <i class="fas fa-user"></i>
      </button>
    </header>
    
    <main class="main-content">
      <div class="card">
        <div class="card-header">
          <h2><i class="fas fa-star mr-3 text-yellow-500"></i>收藏内容</h2>
          <p>共 <span class="font-bold text-blue-morandi-600">{{ visibleItems }}</span> 条收藏记录</p>
        </div>
        
        <div class="card-body">
          <!-- 搜索和筛选区域 -->
          <div class="search-box">
            <i class="fas fa-search"></i>
            <input type="text" placeholder="搜索收藏内容..." v-model="searchTerm" @input="handleSearch">
            <!-- 联想列表 -->
            <ul id="searchSuggestions" class="absolute mt-1 w-full bg-white rounded-md shadow-lg max-h-60 overflow-y-auto z-10" 
                v-show="showSuggestions && filteredSuggestions.length > 0">
              <li v-for="(suggestion, index) in filteredSuggestions" 
                  :key="index" 
                  class="px-4 py-2 hover:bg-blue-morandi-50 cursor-pointer"
                  :class="{ 'active': activeSuggestionIndex === index }"
                  @click="selectSuggestion(suggestion)">
                {{ suggestion }}
              </li>
            </ul>
          </div>
          
          <!-- 收藏内容列表 -->
          <div id="collectionsList">
            <div v-for="(item, index) in filteredCollections" 
                 :key="index" 
                 class="collected-item"
                 :data-type="item.type"
                 :data-date="item.date"
                 :data-views="item.views"
                 :data-tags="item.tags.join(',')">
              <div class="flex items-start">
                <div class="icon-container" :class="item.type">
                  <i :class="getIconClass(item.type)"></i>
                </div>
                <div class="flex-1">
                  <div class="flex justify-between items-center mb-2">
                    <h3>{{ item.title }}</h3>
                    <span class="meta">收藏于 {{ item.date }}</span>
                  </div>
                  <p>{{ item.description }}</p>
                  <div class="tags">
                    <span v-for="(tag, tagIndex) in item.tags" :key="tagIndex" class="tag">{{ tag }}</span>
                  </div>
                  <div class="info">
                    <span><i class="fas fa-eye mr-1"></i> {{ item.views }}浏览</span>
                    <span><i class="fas fa-comment mr-1"></i> {{ item.comments }}评论</span>
                    <span><i class="fas fa-bookmark mr-1 text-yellow-500"></i> 已收藏</span>
                  </div>
                </div>
              </div>
              <div class="actions">
                <div>
                  <button class="share-btn" @click="shareItem(item)">
                    <i class="fas fa-share-alt"></i> 分享
                  </button>
                </div>
                <div class="flex gap-2">
                  <button class="view-btn" @click="viewItem(item)">
                    <i class="fas fa-external-link-alt"></i> 查看原文
                  </button>
                  <button class="unbookmark-btn" @click="removeFromFavorites(index)">
                    <i class="fas fa-bookmark"></i> 取消收藏
                  </button>
                </div>
              </div>
            </div>
          </div>
          
          <!-- 分页控件 -->
          <div class="pagination">
            <button class="page-btn" :class="{ 'disabled': currentPage === 1 }" @click="changePage(currentPage - 1)">
              <i class="fas fa-chevron-left"></i>
            </button>
            <button v-for="page in totalPages" 
                    :key="page" 
                    class="page-btn" 
                    :class="{ 'active': page === currentPage }"
                    @click="changePage(page)">
              {{ page }}
            </button>
            <button class="page-btn" :class="{ 'disabled': currentPage === totalPages }" @click="changePage(currentPage + 1)">
              <i class="fas fa-chevron-right"></i>
            </button>
          </div>
        </div>
      </div>
    </main>
    
    <!-- 空状态提示 -->
    <div class="empty-state" v-if="showEmptyState">
      <i class="fas fa-bookmark"></i>
      <h3>暂无收藏内容</h3>
      <p>您还没有收藏任何内容，快去发现精彩内容吧！</p>
      <button class="btn" @click="goToDiscover">
        <i class="fas fa-compass"></i> 去发现
      </button>
    </div>
    
    <!-- 操作提示 -->
    <div class="toast" :class="[toast.type, { 'show': toast.show }]">{{ toast.message }}</div>
  </div>
</template>

<script>
export default {
  name: 'MyFavorites',
  data() {
    return {
      collections: [
        {
          type: 'post',
          title: '心理健康知识：如何应对焦虑',
          description: '这篇文章详细介绍了5种应对焦虑的有效方法，包括呼吸技巧、正念练习等实用内容，帮助您在日常生活中更好地管理情绪...',
          date: '2025-04-15',
          views: 256,
          comments: 32,
          tags: ['心理健康', '焦虑'],
          url: '/post/123'
        },
        {
          type: 'article',
          title: '音乐疗愈入门指南',
          description: '本文从科学角度解释音乐疗愈的原理，并提供了十个简单易行的音乐疗愈练习，适合初学者在家中实践...',
          date: '2025-04-10',
          views: 189,
          comments: 24,
          tags: ['音乐疗愈', '初学者指南'],
          url: '/article/456'
        },
        {
          type: 'music',
          title: '深度放松冥想音乐合集',
          description: '精心挑选的10首深度放松音乐，特别设计的音频频率可以帮助您快速进入冥想状态，缓解压力和焦虑...',
          date: '2025-04-05',
          views: 423,
          comments: 56,
          tags: ['冥想音乐', '放松'],
          url: '/music/789'
        },
        {
          type: 'video',
          title: '情绪管理实用技巧视频课程',
          description: '由心理学专家主讲的情绪管理视频课程，包含8节课程，涵盖情绪识别、接纳、转化等核心内容...',
          date: '2025-03-28',
          views: 578,
          comments: 85,
          tags: ['情绪管理', '视频课程'],
          url: '/video/101'
        }
      ],
      searchTerm: '',
      filteredCollections: [],
      suggestions: ['心理健康', '焦虑缓解', '正念练习', '呼吸技巧', '情绪管理'],
      filteredSuggestions: [],
      showSuggestions: false,
      activeSuggestionIndex: -1,
      currentPage: 1,
      itemsPerPage: 5,
      toast: {
        message: '',
        type: 'success',
        show: false
      }
    };
  },
  computed: {
    visibleItems() {
      return this.filteredCollections.length;
    },
    totalPages() {
      return Math.ceil(this.filteredCollections.length / this.itemsPerPage);
    },
    showEmptyState() {
      return this.filteredCollections.length === 0;
    }
  },
  created() {
    this.filteredCollections = [...this.collections];
  },
  methods: {
    getIconClass(type) {
      const icons = {
        post: 'fas fa-thumbtack',
        article: 'fas fa-file-alt',
        music: 'fas fa-music',
        video: 'fas fa-video'
      };
      return icons[type] || 'fas fa-bookmark';
    },
    handleSearch() {
      // Filter suggestions
      if (this.searchTerm) {
        this.filteredSuggestions = this.suggestions.filter(s => 
          s.toLowerCase().includes(this.searchTerm.toLowerCase())
        );
        this.showSuggestions = true;
      } else {
        this.filteredSuggestions = [];
        this.showSuggestions = false;
        this.filteredCollections = [...this.collections];
        return;
      }

      // Filter collections
      this.searchCollections(this.searchTerm);
    },
    searchCollections(term) {
      if (!term) {
        this.filteredCollections = [...this.collections];
        return;
      }

      term = term.toLowerCase();
      this.filteredCollections = this.collections.filter(item => {
        return item.title.toLowerCase().includes(term) || 
               item.description.toLowerCase().includes(term) || 
               item.tags.some(tag => tag.toLowerCase().includes(term));
      });
      
      this.currentPage = 1;
    },
    selectSuggestion(suggestion) {
      this.searchTerm = suggestion;
      this.searchCollections(suggestion);
      this.showSuggestions = false;
    },
    changePage(page) {
      if (page < 1 || page > this.totalPages || page === this.currentPage) {
        return;
      }
      this.currentPage = page;
      this.showToast(`已加载第${page}页`, 'success');
    },
    removeFromFavorites(index) {
      const item = this.filteredCollections[index];
      this.showToast('已取消收藏', 'success');
      
      // Remove from filtered and original collections
      this.filteredCollections.splice(index, 1);
      const originalIndex = this.collections.findIndex(c => 
        c.title === item.title && c.date === item.date
      );
      if (originalIndex !== -1) {
        this.collections.splice(originalIndex, 1);
      }
    },
    shareItem(item) {
      // In a real app, this would use the Web Share API or similar
      this.showToast('已复制分享链接', 'success');
    },
    viewItem(item) {
      // In a real app, this would navigate to the item's page
      this.$router?.push(item.url) || (window.location.href = item.url);
    },
    goToDiscover() {
      // Navigate to discovery page
      this.$router?.push('/discover') || (window.location.href = '/discover');
    },
    showToast(message, type = 'success') {
      this.toast.message = message;
      this.toast.type = type;
      this.toast.show = true;
      
      setTimeout(() => {
        this.toast.show = false;
      }, 3000);
    }
  }
};
</script>

<style scoped>
.header {
    background-color: #f5f9fc;
    padding: 1.5rem 2rem;
    border-bottom: 1px solid #e2ecf4;
    display: flex;
    justify-content: space-between;
    align-items: center;
    position: relative;
}

.header h1 {
    font-size: 1.5rem;
    font-weight: 700;
    color: #2c3e50;
    margin: 0;
    flex-grow: 1;
    text-align: center;
}

.header p {
    font-size: 0.875rem;
    color: #64748b;
    margin: 0.25rem 0 0;
    text-align: center;
}

.exit-btn,
.user-btn {
    background: none;
    border: none;
    color: #475569;
    font-size: 1.25rem;
    cursor: pointer;
    padding: 0.5rem;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: all 0.2s;
}

.exit-btn:hover,
.user-btn:hover {
    background-color: rgba(100, 116, 139, 0.1);
    color: #334155;
}

.main-content {
    max-width: 1000px;
    margin: 1rem auto;
    padding: 0 1rem;
}

.card {
    background-color: white;
    border-radius: 0.75rem;
    box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.05), 0 2px 4px -1px rgba(0, 0, 0, 0.03);
    overflow: hidden;
    margin-bottom: 1.5rem;
}

.card-header {
    background-color: #f9fafc;
    padding: 1rem 1.5rem;
    border-bottom: 1px solid #e5eaef;
}

.card-header h2 {
    font-size: 1.25rem;
    font-weight: 600;
    color: #334155;
    margin: 0;
}

.card-header p {
    font-size: 0.875rem;
    color: #64748b;
    margin: 0.25rem 0 0;
}

.card-body {
    padding: 1.5rem;
}

.search-box {
    position: relative;
    margin-bottom: 1.5rem;
}

.search-box i {
    position: absolute;
    left: 1rem;
    top: 50%;
    transform: translateY(-50%);
    color: #94a3b8;
}

.search-box input {
    width: 100%;
    padding: 0.75rem 1rem 0.75rem 2.5rem;
    border: 1px solid #e2e8f0;
    border-radius: 0.5rem;
    font-size: 0.9rem;
    background-color: #f8fafc;
    transition: all 0.2s;
}

.search-box input:focus {
    outline: none;
    border-color: #93c5fd;
    background-color: white;
    box-shadow: 0 0 0 3px rgba(147, 197, 253, 0.25);
}

.collected-item {
    background-color: white;
    border: 1px solid #e5eaef;
    border-radius: 0.5rem;
    padding: 1.25rem;
    margin-bottom: 1rem;
    transition: all 0.2s;
}

.collected-item:hover {
    box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.05), 0 2px 4px -1px rgba(0, 0, 0, 0.03);
    border-color: #cbd5e1;
}

.icon-container {
    width: 2.5rem;
    height: 2.5rem;
    border-radius: 0.5rem;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-right: 1rem;
    flex-shrink: 0;
}

.icon-container.post {
    background-color: #f0f9ff;
    color: #3b82f6;
}

.icon-container.article {
    background-color: #f7fee7;
    color: #84cc16;
}

.icon-container.music {
    background-color: #fef2f2;
    color: #ef4444;
}

.icon-container.video {
    background-color: #ecfeff;
    color: #06b6d4;
}

.collected-item h3 {
    font-size: 1.125rem;
    font-weight: 600;
    color: #1e293b;
    margin: 0;
}

.meta {
    font-size: 0.75rem;
    color: #94a3b8;
}

.collected-item p {
    font-size: 0.875rem;
    color: #64748b;
    margin: 0.5rem 0;
    line-height: 1.5;
}

.tags {
    display: flex;
    flex-wrap: wrap;
    gap: 0.5rem;
    margin: 0.75rem 0;
}

.tag {
    background-color: #f1f5f9;
    color: #64748b;
    font-size: 0.75rem;
    padding: 0.25rem 0.75rem;
    border-radius: 9999px;
}

.info {
    display: flex;
    gap: 1rem;
    font-size: 0.75rem;
    color: #94a3b8;
}

.actions {
    display: flex;
    justify-content: space-between;
    margin-top: 1rem;
    padding-top: 1rem;
    border-top: 1px solid #f1f5f9;
}

.share-btn,
.view-btn,
.unbookmark-btn {
    background: none;
    border: none;
    padding: 0.5rem 0.75rem;
    border-radius: 0.375rem;
    font-size: 0.875rem;
    cursor: pointer;
    display: inline-flex;
    align-items: center;
    gap: 0.5rem;
    transition: all 0.2s;
}

.share-btn {
    color: #64748b;
}

.share-btn:hover {
    color: #475569;
    background-color: #f1f5f9;
}

.view-btn {
    color: #3b82f6;
    background-color: #f0f9ff;
}

.view-btn:hover {
    background-color: #e0f2fe;
}

.unbookmark-btn {
    color: #f59e0b;
    background-color: #fffbeb;
}

.unbookmark-btn:hover {
    background-color: #fef3c7;
}

.pagination {
    display: flex;
    justify-content: center;
    gap: 0.5rem;
    margin-top: 1.5rem;
}

.page-btn {
    background: none;
    border: 1px solid #e2e8f0;
    padding: 0.5rem 0.75rem;
    border-radius: 0.375rem;
    font-size: 0.875rem;
    cursor: pointer;
    min-width: 2.5rem;
    display: inline-flex;
    align-items: center;
    justify-content: center;
    transition: all 0.2s;
}

.page-btn:hover:not(.disabled) {
    border-color: #cbd5e1;
    background-color: #f8fafc;
}

.page-btn.active {
    background-color: #3b82f6;
    color: white;
    border-color: #3b82f6;
}

.page-btn.disabled {
    color: #cbd5e1;
    cursor: not-allowed;
}

.empty-state {
    text-align: center;
    padding: 4rem 1rem;
    color: #94a3b8;
}

.empty-state i {
    font-size: 3rem;
    margin-bottom: 1.5rem;
    color: #cbd5e1;
}

.empty-state h3 {
    font-size: 1.25rem;
    font-weight: 600;
    color: #475569;
    margin: 0;
}

.empty-state p {
    font-size: 1rem;
    margin: 0.5rem 0 1.5rem;
}

.btn {
    background-color: #3b82f6;
    color: white;
    border: none;
    padding: 0.75rem 1.25rem;
    border-radius: 0.375rem;
    font-size: 0.875rem;
    font-weight: 500;
    cursor: pointer;
    display: inline-flex;
    align-items: center;
    gap: 0.5rem;
    transition: all 0.2s;
}

.btn:hover {
    background-color: #2563eb;
}

.toast {
    position: fixed;
    bottom: 1.5rem;
    left: 50%;
    transform: translateX(-50%);
    padding: 0.75rem 1.5rem;
    border-radius: 0.375rem;
    color: white;
    font-size: 0.875rem;
    z-index: 50;
    opacity: 0;
    transition: opacity 0.3s;
}

.toast.show {
    opacity: 1;
}

.toast.success {
    background-color: #10b981;
}

.toast.error {
    background-color: #ef4444;
}

#searchSuggestions {
    background-color: white;
    border: 1px solid #e2e8f0;
    border-radius: 0.375rem;
    box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
}

#searchSuggestions li {
    padding: 0.5rem 1rem;
    cursor: pointer;
}

#searchSuggestions li:hover,
#searchSuggestions li.active {
    color: #3b82f6;
    background-color: #f0f9ff;
}
</style> 
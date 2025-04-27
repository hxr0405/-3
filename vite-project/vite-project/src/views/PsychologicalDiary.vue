<template>
    <div class="font-sans text-gray-800">
      <header class="header p-6 text-white">
        <div class="container mx-auto max-w-4xl">
          <h1 class="text-3xl font-bold text-center font-serif"><i class="fas fa-book-open mr-3"></i>心灵花园</h1>
          <p class="text-center mt-2 opacity-90">记录您的思绪与情感</p>
        </div>
        <button class="user-btn" title="个人中心" @click="goToProfile">
          <i class="fas fa-user"></i>
        </button>
      </header>
      <main class="p-6 container mx-auto max-w-4xl">
        <!-- 记录新日记卡片 -->
        <div class="card p-0 mb-8 overflow-hidden">
          <div class="card-header p-6">
            <h2 class="text-2xl font-bold font-serif flex items-center">
              <i class="fas fa-pen-fancy mr-3 text-primary-600"></i>书写今日心情
            </h2>
            <p class="text-gray-600 mt-1">记录下您的想法、感受和经历</p>
          </div>
          <div class="p-6">
            <textarea v-model="diaryText" class="w-full border p-4 mb-4 rounded-lg diary-entry" rows="8"
              placeholder="在这里写下您的内心感受、想法和经历...&#10;&#10;今天过得怎么样？有什么特别的事情发生吗？您现在的感受是什么？"></textarea>
            <div class="flex flex-col md:flex-row gap-4">
              <div class="flex-1">
                <label for="tag-input" class="block text-sm font-medium text-gray-700 mb-1">分类标签</label>
                <input type="text" id="tag-input" v-model="tagInput" class="border p-3 rounded-lg w-full"
                  placeholder="例如: 工作, 家庭, 情绪 (用逗号分隔)">
              </div>
              <div class="flex items-end">
                <button @click="saveDiary" class="button-primary px-6 py-3 rounded-lg font-medium w-full md:w-auto">
                  <i class="fas fa-save mr-2"></i>保存日记
                </button>
              </div>
            </div>
          </div>
        </div>
  
        <!-- 筛选与搜索卡片 -->
        <div class="card p-0 mb-8">
          <div class="card-header p-6">
            <h2 class="text-2xl font-bold font-serif flex items-center">
              <i class="fas fa-filter mr-3 text-primary-600"></i>筛选日记
            </h2>
            <p class="text-gray-600 mt-1">查找特定的日记条目</p>
          </div>
          <div class="p-6">
            <div class="grid grid-cols-1 md:grid-cols-3 gap-4 mb-4">
              <div>
                <label for="search-input" class="block text-sm font-medium text-gray-700 mb-1">关键词搜索</label>
                <input type="text" id="search-input" v-model="searchText" class="border p-3 rounded-lg w-full" placeholder="搜索日记内容">
              </div>
              <div>
                <label for="date-filter" class="block text-sm font-medium text-gray-700 mb-1">日期筛选</label>
                <input type="date" id="date-filter" v-model="dateFilter" class="border p-3 rounded-lg w-full">
              </div>
              <div class="flex items-end space-x-2">
                <button @click="filterDiaries" class="button-primary px-4 py-3 rounded-lg font-medium flex-1">
                  <i class="fas fa-search mr-2"></i>筛选
                </button>
                <button @click="clearFilters" class="button-secondary px-4 py-3 rounded-lg font-medium">
                  <i class="fas fa-undo mr-2"></i>
                </button>
              </div>
            </div>
          </div>
        </div>
  
        <!-- 过往日记卡片 -->
        <div class="card p-0">
          <div class="card-header p-6">
            <div class="flex justify-between items-center">
              <div>
                <h2 class="text-2xl font-bold font-serif flex items-center">
                  <i class="fas fa-history mr-3 text-primary-600"></i>日记回忆
                </h2>
                <p class="text-gray-600 mt-1">回顾您的心路历程</p>
              </div>
              <button @click="exportDiaries" class="button-secondary px-4 py-2 rounded-lg text-sm">
                <i class="fas fa-file-export mr-2"></i>导出
              </button>
            </div>
          </div>
          <div class="p-6">
            <div>
              <ul v-if="filteredDiaries.length > 0" class="space-y-4">
                <li v-for="(entry, index) in filteredDiaries" :key="index" class="border border-gray-100 p-6 rounded-lg bg-white shadow-sm">
                  <div class="flex justify-between items-start mb-3">
                    <div class="flex items-center">
                      <i class="fas fa-calendar-day text-primary-500 mr-2"></i>
                      <span class="text-sm font-medium text-gray-600">{{ entry.date }}</span>
                    </div>
                    <button class="text-gray-400 hover:text-red-500 transition-colors" @click="deleteDiary(index)" title="删除日记">
                      <i class="fas fa-trash"></i>
                    </button>
                  </div>
                  <p class="text-gray-800 mb-4 leading-relaxed whitespace-pre-line">{{ entry.text }}</p>
                  <div class="flex flex-wrap gap-2">
                    <span v-for="(tag, tagIndex) in entry.tags" :key="tagIndex" class="tag">
                      <i class="fas fa-tag mr-1 text-xs"></i>{{ tag }}
                    </span>
                  </div>
                </li>
              </ul>
              <div v-else class="empty-state p-8 text-center rounded-lg">
                <i class="fas fa-book-open text-4xl text-gray-400 mb-4"></i>
                <h3 class="text-xl font-medium text-gray-600">暂无日记记录</h3>
                <p class="text-gray-500 mt-2">点击上方"书写今日心情"开始记录您的第一份心理日记</p>
              </div>
            </div>
          </div>
        </div>
      </main>
      <div :class="['toast', { show: showToast }]">{{ toastMessage }}</div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'PsychologicalDiary',
    data() {
      return {
        diaryText: '',
        tagInput: '',
        searchText: '',
        dateFilter: '',
        allDiaries: [],
        filteredDiaries: [],
        toastMessage: '',
        showToast: false
      }
    },
    created() {
      this.loadDiaries();
    },
    methods: {
      loadDiaries() {
        const savedDiaries = localStorage.getItem('diaries');
        if (savedDiaries) {
          this.allDiaries = JSON.parse(savedDiaries);
        } else {
          this.allDiaries = [];
        }
        this.filteredDiaries = [...this.allDiaries];
      },
      saveDiary() {
        const entryText = this.diaryText.trim();
        const tags = this.tagInput.split(',').map(tag => tag.trim()).filter(tag => tag);
  
        if (entryText) {
          const newEntry = {
            text: entryText,
            date: new Date().toLocaleString('zh-CN', {
              year: 'numeric',
              month: '2-digit',
              day: '2-digit',
              hour: '2-digit',
              minute: '2-digit',
              second: '2-digit',
              hour12: false
            }),
            tags: tags
          };
  
          this.allDiaries.unshift(newEntry);
          localStorage.setItem('diaries', JSON.stringify(this.allDiaries));
          this.filteredDiaries = [...this.allDiaries];
          this.diaryText = '';
          this.tagInput = '';
          this.showToastMessage('日记保存成功 🌟');
  
          // 滚动到顶部查看新添加的日记
          window.scrollTo({
            top: 0,
            behavior: 'smooth'
          });
        } else {
          this.showToastMessage('请先写下您的日记内容 ✏️');
        }
      },
      deleteDiary(index) {
        if (confirm('确定要删除这篇日记吗？此操作无法撤销。')) {
          const diaryIndex = this.allDiaries.indexOf(this.filteredDiaries[index]);
          if (diaryIndex !== -1) {
            this.allDiaries.splice(diaryIndex, 1);
            localStorage.setItem('diaries', JSON.stringify(this.allDiaries));
            this.filteredDiaries.splice(index, 1);
            this.showToastMessage('日记已删除 🗑️');
          }
        }
      },
      filterDiaries() {
        const searchText = this.searchText.toLowerCase();
        const filterDate = this.dateFilter;
  
        this.filteredDiaries = this.allDiaries.filter(entry => {
          const matchesSearch = entry.text.toLowerCase().includes(searchText) || 
                               entry.tags.some(tag => tag.toLowerCase().includes(searchText));
  
          const entryDate = new Date(entry.date).toISOString().split('T')[0];
          const matchesDate = filterDate ? entryDate === filterDate : true;
  
          return matchesSearch && matchesDate;
        });
  
        if (this.filteredDiaries.length === 0 && (searchText || filterDate)) {
          this.showToastMessage('没有找到符合条件的日记 🔍');
        }
      },
      clearFilters() {
        this.searchText = '';
        this.dateFilter = '';
        this.filteredDiaries = [...this.allDiaries];
        this.showToastMessage('已清除所有筛选条件 ✨');
      },
      exportDiaries() {
        if (this.allDiaries.length === 0) {
          this.showToastMessage('没有日记可导出，请先记录日记 📝');
          return;
        }
  
        let csvContent = "data:text/csv;charset=utf-8,日期,内容,标签\n";
        this.allDiaries.forEach(entry => {
          const date = entry.date;
          const text = entry.text.replace(/"/g, '""');
          const tags = entry.tags.join(', ');
          csvContent += `"${date}","${text}","${tags}"\n`;
        });
  
        const encodedUri = encodeURI(csvContent);
        const link = document.createElement("a");
        link.setAttribute("href", encodedUri);
        link.setAttribute("download", `心理日记_${new Date().toLocaleDateString()}.csv`);
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
  
        this.showToastMessage('日记已导出为CSV文件 📂');
      },
      goToProfile() {
        this.$router.push('/personal-page');
      },
      showToastMessage(message) {
        this.toastMessage = message;
        this.showToast = true;
  
        setTimeout(() => {
          this.showToast = false;
        }, 3000);
      }
    }
  }
  </script>
  
  <style scoped>
  body {
    background-color: #e6f7ff;
    min-height: 100vh;
    transition: all 0.3s ease;
  }
  
  .header {
    background: linear-gradient(135deg, #93d5f2 0%, #76cff8 100%);
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
  }
  
  .card {
    background-color: white;
    border-radius: 12px;
    box-shadow: 0 4px 14px 0 rgba(0, 0, 0, 0.08);
    transition: all 0.3s ease;
    border: 1px solid rgba(0, 0, 0, 0.05);
  }
  
  .card:hover {
    transform: translateY(-2px);
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
  }
  
  .card-header {
    border-bottom: 1px solid rgba(0, 0, 0, 0.05);
    background-color: rgba(249, 249, 249, 0.8);
  }
  
  .button-primary {
    background: linear-gradient(135deg, #93d5f2 0%, #4db4e0 100%);
    color: white;
    transition: all 0.3s ease;
    box-shadow: 0 2px 6px rgba(0, 89, 217, 0.3);
  }
  
  .button-primary:hover {
    transform: translateY(-1px);
    box-shadow: 0 4px 12px rgba(0, 89, 217, 0.4);
    background: linear-gradient(135deg, #93d5f2 0%, #1796cd 100%);
  }
  
  .button-secondary {
    background: linear-gradient(135deg, #83b8e3 0%, #5097cd 100%);
    color: white;
    transition: all 0.3s ease;
    box-shadow: 0 2px 6px rgba(51, 171, 219, 0.3);
  }
  
  .button-secondary:hover {
    transform: translateY(-1px);
    box-shadow: 0 4px 12px rgba(11, 175, 225, 0.4);
    background: linear-gradient(135deg, #84a4d5 0%, #346cb0 100%);
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
  
  textarea,
  input {
    transition: all 0.3s ease;
    border: 1px solid rgba(0, 0, 0, 0.1);
  }
  
  textarea:focus,
  input:focus {
    border-color: #0059d9;
    box-shadow: 0 0 0 3px rgba(0, 89, 217, 0.2);
    outline: none;
  }
  
  .tag {
    display: inline-block;
    background-color: #b3e6ff;
    color: #093261;
    padding: 2px 8px;
    border-radius: 12px;
    font-size: 0.8rem;
    margin-right: 4px;
    margin-bottom: 4px;
  }
  
  .diary-entry {
    background-color: #fefcf9;
    border-left: 4px solid #80d5ff;
    padding-left: 12px;
  }
  
  .empty-state {
    background-color: #f4f5f7;
    border: 2px dashed #80d5ff;
    border-radius: 12px;
  }
  
  @media (max-width: 768px) {
    .header h1 {
      font-size: 1.8rem;
    }
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
  </style> 
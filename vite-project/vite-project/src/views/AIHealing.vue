<template>
  <div class="ai-healing">
    <div class="sidebar" id="sidebar">
      <div style="padding: 12px 15px; margin-bottom: 10px;">
        <i class="fa-solid fa-bars toggle-sidebar" id="toggle-sidebar"></i>
      </div>
      <div class="sidebar-item" id="add-new-dialog">
        <i class="fa-solid fa-plus"></i>
        <span>添加新对话</span>
      </div>
      <div class="sidebar-item" id="ai-recommend">
        <i class="fa-solid fa-star"></i>
        <span>AI 推荐</span>
      </div>
      <div class="sidebar-item" id="history-dialog-trigger">
        <i class="fa-solid fa-history"></i>
        <span>历史对话</span>
      </div>
    </div>
    <div class="main-content">
      <div class="top-nav">
        <div class="logo">
          <router-link to="/">HEARTCURE</router-link>
        </div>
        <div class="nav-description">用 AI 科技，开启心灵疗愈之旅</div>
        <div class="nav-links">
          <router-link to="/healing-music-library" class="nav-link" id="healing-link">
            <i class="fa-solid fa-heart-pulse"></i>
            疗愈
          </router-link>
        </div>
      </div>
      <div class="status-indicator" id="typing-indicator" v-show="isTyping">
        <div class="typing-animation">
          <div class="dot"></div>
          <div class="dot"></div>
          <div class="dot"></div>
        </div>
        AI 正在思考中...
      </div>
      <div class="chat-content" id="chat-content" v-show="currentPage === 'chat'">
        <div class="greeting">
          你好，欢迎来到 AI 助愈空间！
          <p>在这里，你可以畅所欲言，分享你的感受与困扰，我会尽力为你提供帮助。</p>
        </div>
        <div class="chat-box" id="chat-box">
          <div v-for="(message, index) in chatMessages" :key="index" 
               :class="['chat-message', message.from === 'user' ? 'user-message' : 'ai-message']">
            {{ message.from === 'user' ? '你：' : 'AI：' }}{{ message.text }}
          </div>
        </div>
        <div class="input-container" id="input-container">
          <input type="text" class="input-bar" id="input-bar" 
                 placeholder="发消息、输入 @ 选择技能或输入需求进行搜索" 
                 v-model="inputMessage" 
                 @keydown.enter="sendChatMessage">
          <button class="input-action" id="send-button" aria-label="发送消息" @click="sendChatMessage">
            <i class="fa-solid fa-paper-plane"></i>
          </button>
        </div>
        <div class="suggestion-panel" id="suggestion-panel" v-show="showSuggestionPanel">
          <div v-if="inputMessage.startsWith('@')">
            <div class="suggestion-item" v-for="skill in skills" :key="skill" @click="insertSuggestion('@' + skill)">
              <i class="fa-solid fa-wand-magic-sparkles"></i>
              {{ skill }}
            </div>
          </div>
          <div v-else-if="inputMessage.startsWith('/')">
            <label class="file-upload">
              <input type="file" hidden>
              <i class="fa-solid fa-file-arrow-up"></i>
              上传文件
            </label>
            <div class="file-option" @click="createMindMap">
              <i class="fa-solid fa-diagram-project"></i>
              新建脑图
            </div>
          </div>
        </div>
      </div>
      <div class="ai-recommend-page" id="ai-recommend-page" v-show="currentPage === 'recommend'">
        <div class="recommend-header">
          <h2>AI 推荐内容</h2>
        </div>
        <div class="recommend-item" data-keywords="音乐 放松 舒缓" @click="goToPlaylistType('放松')">
          <div class="recommend-title">音乐疗愈推荐</div>
          <div class="recommend-description">
            我们为你精心挑选了一系列舒缓的音乐，这些音乐巧妙融合了自然音效与轻柔旋律。
            例如，鸟鸣声与悠扬的钢琴旋律交织，能让你仿佛置身于宁静的森林中，帮助你放松身心，
            有效缓解日常压力，让你轻松进入宁静的状态。无论是在忙碌的工作间隙，还是在睡前，
            这些音乐都能成为你心灵的慰藉。
          </div>
        </div>
        <div class="recommend-item" data-keywords="文章 心灵成长 启发思考">
          <div class="recommend-title">文章阅读推荐</div>
          <div class="recommend-description">
            这里提供的文章涵盖了心理学、自我成长、心灵鸡汤等多个领域。每一篇都经过精心挑选，
            比如有关于情绪管理的实用技巧文章，能帮助你更好地认识和调节自己的情绪；
            还有自我成长类的故事，激励你不断挑战自我，实现个人突破。这些文章旨在启发你的思考，
            滋养你的心灵，给予你面对生活的力量和智慧。
          </div>
        </div>
        <div class="recommend-item" data-keywords="冥想 减压 专注力提升" @click="goToPlaylistType('减压')">
          <div class="recommend-title">冥想练习推荐</div>
          <div class="recommend-description">
            我们推荐的冥想练习由专业的冥想导师指导，包含了不同时长和难度的课程。
            从基础的呼吸冥想开始，你可以学会专注于自己的呼吸，排除杂念；
            到深度的身体扫描冥想，帮助你全面放松身体的每一个部位。
            通过这些冥想练习，你可以有效减轻压力与焦虑，提升专注力和内心的平静。
            无论你是初学者还是有一定经验的冥想者，都能在这里找到适合自己的课程。
          </div>
        </div>
      </div>
      <div class="history-page" v-show="currentPage === 'history'">
        <div class="page-header">
          <span class="close-icon history-dialog-close">&times;</span>
          <h2>历史对话</h2>
        </div>
        <h2>历史对话记录</h2>
        <ul>
          <li>对话 1 - 2025 年 4 月 15 日</li>
          <li>对话 2 - 2025 年 4 月 16 日</li>
          <li>对话 3 - 2025 年 4 月 17 日</li>
        </ul>
      </div>
      <div class="healing-page" id="healing-page" v-show="currentPage === 'healing'">
        <h2>疗愈页面</h2>
        <p>这里是疗愈相关的内容。</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AIHealing',
  data() {
    return {
      currentPage: 'chat', // chat, recommend, history, healing
      chatMessages: [
        { from: 'ai', text: '你好，我是 AI 心理助手，请告诉我你今天感觉如何？' },
      ],
      inputMessage: '',
      isTyping: false,
      showSuggestionPanel: false,
      skills: ['心理咨询', '情绪分析', '睡眠改善', '压力管理'],
      // 模拟智能回复模板
      responseTemplates: {
        stress: [
          "我注意到您提到压力问题，建议尝试我们的呼吸练习：找一个安静的地方坐下或躺下，慢慢地吸气，让空气充满腹部，然后再缓缓地呼气，重复几次，每次呼吸尽量保持均匀和深沉。",
          "渐进式肌肉放松法可能有帮助：先紧绷身体的某一组肌肉，保持几秒钟，然后突然放松，感受肌肉从紧张到放松的变化，依次对全身的肌肉组进行这样的练习。"
        ],
        sleep: [
          "睡眠问题通常与作息规律有关，建议：保持固定的作息时间，每天尽量在相同的时间上床睡觉和起床；睡前避免使用电子设备，因为屏幕发出的蓝光会抑制褪黑素的分泌。",
          "试试我们的白噪音助眠合集：白噪音可以帮助屏蔽外界的干扰声音，营造一个安静的睡眠环境，比如雨声、海浪声等，你可以在我们的音乐疗愈推荐中找到相关内容。"
        ],
        anxiety: [
          "当感到焦虑时，可以尝试'5-4-3-2-1'感官练习：列出5个你能看到的东西，4个你能触摸到的东西，3个你能听到的声音，2个你能闻到的气味，和1个你能尝到的味道。这有助于让你回到当下，减轻焦虑感。",
          "呼吸练习对缓解焦虑很有效：尝试4-7-8呼吸法，吸气4秒，屏息7秒，呼气8秒。这个方法可以帮助重新平衡自主神经系统，减轻焦虑症状。"
        ],
        default: [
          "我理解您的感受，我们可以一起探索解决方法：可以详细描述一下您的问题，这样我能更好地为您提供帮助。",
          "感谢您分享您的感受。您可以尝试更具体地描述您的情况，这样我能提供更有针对性的建议。"
        ]
      }
    }
  },
  mounted() {
    // 设置侧边栏切换事件
    const toggleSidebar = document.getElementById('toggle-sidebar');
    if (toggleSidebar) {
      toggleSidebar.addEventListener('click', this.toggleSidebar);
    }
    
    // 设置侧边栏项目点击事件
    const addNewDialog = document.getElementById('add-new-dialog');
    const aiRecommend = document.getElementById('ai-recommend');
    const historyDialogTrigger = document.getElementById('history-dialog-trigger');
    
    if (addNewDialog) {
      addNewDialog.addEventListener('click', () => this.showPage('chat'));
    }
    
    if (aiRecommend) {
      aiRecommend.addEventListener('click', () => this.showPage('recommend'));
    }
    
    if (historyDialogTrigger) {
      historyDialogTrigger.addEventListener('click', () => {
        this.showPage('history');
      });
    }
    
    // 监听输入框变化
    const inputBar = document.getElementById('input-bar');
    if (inputBar) {
      inputBar.addEventListener('input', this.handleInputChange);
    }
    
    // 监听历史对话关闭按钮
    const historyDialogClose = document.querySelector('.history-dialog-close');
    if (historyDialogClose) {
      historyDialogClose.addEventListener('click', () => {
        this.showPage('chat');
      });
    }
  },
  methods: {
    // 侧边栏切换
    toggleSidebar() {
      const sidebar = document.getElementById('sidebar');
      if (sidebar) {
        sidebar.classList.toggle('collapsed');
      }
    },
    
    // 显示指定页面
    showPage(pageName) {
      this.currentPage = pageName;
      
      if (pageName === 'chat') {
        const inputBar = document.getElementById('input-bar');
        if (inputBar) {
          inputBar.placeholder = '发消息、输入 @ 选择技能或输入需求进行搜索';
        }
      }
    },
    
    // 处理输入框变化
    handleInputChange(e) {
      const value = e.target.value;
      this.inputMessage = value;
      
      if (value.startsWith('@')) {
        this.showSuggestionPanel = true;
      } else if (value.startsWith('/')) {
        this.showSuggestionPanel = true;
      } else {
        this.showSuggestionPanel = false;
      }
    },
    
    // 插入建议
    insertSuggestion(suggestion) {
      this.inputMessage = suggestion;
      this.showSuggestionPanel = false;
      
      // 聚焦到输入框
      this.$nextTick(() => {
        const inputBar = document.getElementById('input-bar');
        if (inputBar) {
          inputBar.focus();
        }
      });
    },
    
    // 创建脑图
    createMindMap() {
      alert('正在创建脑图...');
      this.showSuggestionPanel = false;
    },
    
    // 发送聊天消息
    sendChatMessage() {
      if (!this.inputMessage.trim()) return;
      
      // 添加用户消息
      this.chatMessages.push({
        from: 'user',
        text: this.inputMessage
      });
      
      const userMessage = this.inputMessage;
      this.inputMessage = '';
      this.showSuggestionPanel = false;
      
      // 显示正在输入状态
      this.isTyping = true;
      
      // 滚动到最新消息
      this.$nextTick(() => {
        const chatBox = document.getElementById('chat-box');
        if (chatBox) {
          chatBox.scrollTop = chatBox.scrollHeight;
        }
      });
      
      // 模拟AI回复
      setTimeout(() => {
        // 根据消息内容选择回复模板
        let response;
        if (userMessage.includes('压力')) {
          const templates = this.responseTemplates.stress;
          response = templates[Math.floor(Math.random() * templates.length)];
        } else if (userMessage.includes('睡眠') || userMessage.includes('失眠')) {
          const templates = this.responseTemplates.sleep;
          response = templates[Math.floor(Math.random() * templates.length)];
        } else if (userMessage.includes('焦虑') || userMessage.includes('紧张')) {
          const templates = this.responseTemplates.anxiety;
          response = templates[Math.floor(Math.random() * templates.length)];
        } else {
          const templates = this.responseTemplates.default;
          response = templates[Math.floor(Math.random() * templates.length)];
        }
        
        // 添加AI回复
        this.chatMessages.push({
          from: 'ai',
          text: response
        });
        
        // 隐藏正在输入状态
        this.isTyping = false;
        
        // 滚动到最新消息
        this.$nextTick(() => {
          const chatBox = document.getElementById('chat-box');
          if (chatBox) {
            chatBox.scrollTop = chatBox.scrollHeight;
          }
        });
      }, 1500);
    },
    
    // 导航到 PlaylistType 页面
    goToPlaylistType(type) {
      this.$router.push({
        name: 'PlaylistType',
        query: { type: type }
      });
    }
  }
}
</script>

<style scoped>
/* 全局样式 */
.ai-healing {
  font-family: 'Inter', sans-serif;
  background: linear-gradient(to bottom, #f9fafb, #e1f4f9);
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0;
  min-height: 100vh;
}

/* 侧边栏样式 */
.sidebar {
  width: 250px;
  background: #ffffff;
  box-shadow: 2px 0 8px rgba(0, 0, 0, 0.08);
  height: 90vh;
  padding: 20px;
  transition: width 0.3s ease, box-shadow 0.3s ease;
  position: relative;
  overflow-y: auto;
  border-radius: 12px;
  margin-right: 20px;
}

.sidebar:hover {
  box-shadow: 2px 0 12px rgba(0, 0, 0, 0.12);
}

.sidebar.collapsed {
  width: 60px;
  padding: 20px 10px;
}

.sidebar-item {
  padding: 12px 15px;
  margin-bottom: 10px;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  color: #4b5e77;
}

.sidebar-item i {
  margin-right: 10px;
  color: #6bacf3;
  font-size: 1.1rem;
  transition: transform 0.3s ease;
}

.sidebar-item:hover {
  background: #e9f6ff;
  transform: translateX(5px);
}

.sidebar-item:hover i {
  transform: scale(1.2);
}

.sidebar.collapsed .sidebar-item span {
  display: none;
}

.toggle-sidebar {
  position: absolute;
  top: 20px;
  right: 20px;
  cursor: pointer;
  color: #6bacf3;
  font-size: 1.2rem;
  transition: transform 0.3s ease;
}

.toggle-sidebar:hover {
  transform: rotate(90deg);
}

/* 主内容区样式 */
.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  height: 90vh;
  max-width: 1200px;
  border-radius: 12px;
  background: #ffffff;
  box-shadow: 0 0 8px rgba(0, 0, 0, 0.08);
  transition: box-shadow 0.3s ease;
}

.main-content:hover {
  box-shadow: 0 0 12px rgba(0, 0, 0, 0.12);
}

/* 顶部导航栏样式 */
.top-nav {
  background: linear-gradient(to right, #ffffff, #f9fcff);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  padding: 20px 40px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  position: relative;
  width: 100%;
  box-sizing: border-box;
  border-top-left-radius: 12px;
  border-top-right-radius: 12px;
}

.logo {
  font-size: 26px;
  font-weight: 800;
  color: #6bacf3;
  display: flex;
  align-items: center;
  transition: color 0.3s ease;
}

.logo:hover {
  color: #4c8fd6;
}

.nav-description {
  color: #777;
  font-size: 14px;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
}

.nav-links {
  display: flex;
  align-items: center;
}

.nav-link {
  margin-left: 20px;
  color: #333;
  text-decoration: none;
  transition: color 0.3s ease, transform 0.3s ease;
  display: flex;
  align-items: center;
}

.nav-link i {
  margin-right: 5px;
  transition: transform 0.3s ease;
}

.nav-link:hover {
  color: #6bacf3;
  transform: translateY(-2px);
}

.nav-link:hover i {
  transform: scale(1.2);
}

/* 聊天内容区样式 */
.chat-content {
  flex: 1;
  padding: 40px 20px;
  position: relative;
  width: 100%;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.greeting {
  font-size: 28px;
  margin-bottom: 30px;
  color: #333;
  text-align: center;
  animation: fadeIn 0.5s ease;
}

.greeting p {
  font-size: 16px;
  color: #777;
  margin-top: 10px;
}

.input-container {
  width: 90%;
  max-width: 800px;
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 10px;
  position: absolute;
  bottom: 15px;
  left: 50%;
  transform: translateX(-50%);
}

.input-bar {
  flex: 1;
  padding: 12px 18px;
  border: 1px solid #e0e0e0;
  border-radius: 10px;
  font-size: 16px;
  outline: none;
  transition: border-color 0.3s ease, box-shadow 0.3s ease;
  background: #f9fcff;
}

.input-bar:focus {
  border-color: #4c8fd6;
  box-shadow: 0 0 8px rgba(108, 175, 243, 0.2);
}

.input-action {
  background: linear-gradient(135deg, #6bacf3, #4c8fd6);
  border: none;
  border-radius: 10px;
  padding: 12px;
  cursor: pointer;
  transition: transform 0.3s ease, background 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 44px;
  height: 44px;
}

.input-action i {
  color: #ffffff;
  font-size: 1.2rem;
  transition: transform 0.3s ease;
}

.input-action:hover {
  background: linear-gradient(135deg, #4c8fd6, #3b82f6);
  transform: scale(1.1);
}

.input-action:active {
  transform: scale(0.95);
  background: linear-gradient(135deg, #3b82f6, #2b6cbf);
}

.input-action:active i {
  transform: scale(1.2);
}

.chat-box {
  width: 90%;
  max-width: 800px;
  height: calc(100% - 180px);
  border: 1px solid #e0e0e0;
  border-radius: 12px;
  padding: 20px;
  margin-bottom: 10px;
  overflow-y: auto;
  background: #ffffff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  margin: 0 auto;
}

.chat-message {
  padding: 10px 15px;
  border-radius: 8px;
  margin-bottom: 10px;
  max-width: 70%;
  display: flex;
  align-items: center;
  opacity: 0;
  transform: translateY(20px);
  animation: messageAppear 0.3s ease forwards;
}

.user-message {
  background: #e9f6ff;
  align-self: flex-end;
  margin-left: auto;
}

.ai-message {
  background: #f3f6f9;
  align-self: flex-start;
  margin-right: auto;
}

/* AI 推荐页面样式 */
.ai-recommend-page {
  width: 90%;
  max-width: 800px;
  padding: 20px;
  text-align: center;
  background: #ffffff;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  margin: 50px auto;
}

.recommend-header {
  margin-bottom: 20px;
}

.recommend-header h2 {
  font-size: 24px;
  color: #333;
  margin-bottom: 10px;
}

.recommend-item {
  width: 100%;
  margin-bottom: 20px;
  padding: 20px;
  border: 1px solid #e0e0e0;
  border-radius: 10px;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  background: #f9fafb;
  box-sizing: border-box;
  text-align: left;
  cursor: pointer;
}

.recommend-item:hover {
  transform: scale(1.02);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.recommend-title {
  font-size: 20px;
  font-weight: 600;
  color: #333;
  margin-bottom: 10px;
}

.recommend-description {
  color: #777;
  font-size: 14px;
}

/* 历史对话页面样式 */
.history-page {
  width: 90%;
  max-width: 800px;
  padding: 20px;
  text-align: center;
  background: #ffffff;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  margin: 50px auto;
}

.history-page h2 {
  font-size: 24px;
  color: #333;
  margin-bottom: 20px;
}

.history-page ul {
  list-style-type: none;
  padding: 0;
}

.history-page li {
  padding: 10px;
  border-bottom: 1px solid #e0e0e0;
  cursor: pointer;
  transition: background 0.3s ease;
  color: #4b5e77;
}

.history-page li:hover {
  background: #e9f6ff;
}

/* 疗愈页面样式 */
.healing-page {
  width: 90%;
  max-width: 800px;
  padding: 20px;
  text-align: center;
  background: #ffffff;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  margin: 50px auto;
}

/* 消息状态指示器 */
.status-indicator {
  padding: 12px;
  background: #f0f9ff;
  border-radius: 8px;
  margin: 10px auto;
  width: fit-content;
  color: #4b5e77;
}

.typing-animation {
  display: inline-flex;
  align-items: center;
}

.dot {
  width: 8px;
  height: 8px;
  background: #6bacf3;
  border-radius: 50%;
  margin: 0 3px;
  animation: bounce 1.4s infinite;
}

.dot:nth-child(2) {
  animation-delay: 0.2s;
}

.dot:nth-child(3) {
  animation-delay: 0.4s;
}

@keyframes bounce {
  0%, 80%, 100% {
    transform: scale(0);
  }
  40% {
    transform: scale(1);
  }
}

/* 建议面板样式 */
.suggestion-panel {
  position: absolute;
  width: 100%;
  max-width: 800px;
  background: #ffffff;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  z-index: 100;
  top: 50px;
}

.suggestion-item {
  padding: 10px;
  cursor: pointer;
  transition: background 0.2s ease;
  color: #4b5e77;
}

.suggestion-item:hover {
  background: #f5faff;
}

/* 动画效果 */
.fade-in {
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

@keyframes messageAppear {
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 文件上传和选项样式 */
.file-upload {
  display: flex;
  align-items: center;
  padding: 10px;
  cursor: pointer;
  transition: background 0.2s ease;
  color: #4b5e77;
}

.file-upload:hover, .file-option:hover {
  background: #f5faff;
}

.file-option {
  display: flex;
  align-items: center;
  padding: 10px;
  cursor: pointer;
  transition: background 0.2s ease;
  color: #4b5e77;
}

/* 响应式布局 */
@media (max-width: 768px) {
  .ai-healing {
    flex-direction: column;
  }

  .sidebar {
    position: static;
    width: 100%;
    height: auto;
    margin-right: 0;
    margin-bottom: 20px;
    border-radius: 0 0 12px 12px;
  }

  .main-content {
    width: 100%;
    border-radius: 0;
  }

  .chat-box {
    height: calc(100% - 140px);
  }

  .input-container {
    width: 95%;
    gap: 6px;
    margin-bottom: 8px;
    bottom: 10px;
  }

  .top-nav {
    padding: 15px 20px;
  }

  .nav-description {
    display: none;
  }
}
</style>
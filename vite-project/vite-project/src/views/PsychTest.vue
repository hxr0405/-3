<script>
const musicRecommendations = {
  low: [
    { title: '清晨 - 班得瑞', cover: 'https://img.kuwo.cn/star/albumcover/500/17/15/706971015.jpg', reason: '轻柔的自然声音与钢琴旋律，能帮助平静心情，适合情绪波动不大时聆听。' },
    { title: 'River Flows In You - Yiruma', cover: 'https://img.kuwo.cn/star/albumcover/500/64/72/2440726464.jpg', reason: '优美的钢琴曲，温柔而流畅，能带来内心的平和与舒适感。' }
  ],
  medium: [
    { title: 'Weightless - Marconi Union', cover: 'https://img.kuwo.cn/star/albumcover/500/86/40/4085403686.jpg', reason: '科学证明能降低焦虑的音乐，节奏缓慢有助于放松神经系统。' },
    { title: '月光 - 德彪西', cover: 'https://img.kuwo.cn/star/albumcover/500/19/82/3640822819.jpg', reason: '印象派钢琴作品，梦幻般的旋律能引导思绪放松，缓解中等压力。' }
  ],
  high: [
    { title: 'Gymnopédie No.1 - Erik Satie', cover: 'https://img.kuwo.cn/star/albumcover/500/66/42/4258422466.jpg', reason: '极简主义的钢琴曲，缓慢的节奏和简单的旋律能有效安抚高度焦虑的情绪。' },
    { title: 'Spiegel im Spiegel - Arvo Pärt', cover: 'https://img.kuwo.cn/star/albumcover/500/17/15/706971015.jpg', reason: '冥想式的极简音乐，重复而缓慢的旋律能帮助过度活跃的思绪平静下来。' }
  ]
};
const suggestionsData = {
  emotion: [
    { level: 'low', text: '您的情绪状态较为稳定，继续保持良好的情绪调节习惯。建议每天花几分钟记录感恩日记，强化积极情绪。' },
    { level: 'medium', text: '您偶尔会经历情绪波动，这可能与压力或生活事件有关。建议尝试正念冥想或深呼吸练习，每天10-15分钟。' },
    { level: 'high', text: '您的情绪困扰较为明显，建议寻求专业心理咨询师的帮助。同时可以尝试规律运动，如每天30分钟散步或瑜伽。' }
  ],
  stress: [
    { level: 'low', text: '您的压力水平在正常范围内，保持良好的工作生活平衡即可。' },
    { level: 'medium', text: '您感受到一定的压力，建议学习时间管理技巧，确保每天有足够的休息和放松时间。' },
    { level: 'high', text: '您的压力水平较高，可能需要重新评估当前的工作生活安排。考虑与上司或家人沟通调整责任分配，必要时寻求专业帮助。' }
  ],
  relationship: [
    { level: 'low', text: '您的人际关系状态良好，继续保持积极的社交互动。' },
    { level: 'medium', text: '您在社交中偶尔感到疲惫，建议选择质量而非数量的社交活动，留出足够的独处时间恢复能量。' },
    { level: 'high', text: '您可能正在经历社交困难或孤独感，建议从小范围的、低压力的社交活动开始重建连接，或考虑加入支持小组。' }
  ],
  self: [
    { level: 'low', text: '您的自我认知积极健康，继续保持自我关怀的习惯。' },
    { level: 'medium', text: '您对自己的评价有时偏低，建议练习自我同情，像对待好朋友一样对待自己。' },
    { level: 'high', text: '您对自己的评价较为消极，这可能影响整体心理健康。建议通过心理咨询探索自我价值感问题。' }
  ]
};
export default {
  name: 'PsychTest',
  data() {
    return {
      step: 'intro',
      currentIndex: 0,
      answers: [],
      questions: [
        { id: 1, dimension: '情绪', question: '我感到情绪低落或沮丧：', options: [ { text: '几乎没有', score: 0 }, { text: '偶尔', score: 1 }, { text: '经常', score: 2 }, { text: '几乎每天', score: 3 } ] },
        { id: 2, dimension: '情绪', question: '我对未来感到无望或悲观：', options: [ { text: '几乎没有', score: 0 }, { text: '偶尔', score: 1 }, { text: '经常', score: 2 }, { text: '几乎每天', score: 3 } ] },
        { id: 3, dimension: '情绪', question: '我感到焦虑或紧张：', options: [ { text: '几乎没有', score: 0 }, { text: '偶尔', score: 1 }, { text: '经常', score: 2 }, { text: '几乎每天', score: 3 } ] },
        { id: 4, dimension: '压力', question: '我感到难以应对日常任务或责任：', options: [ { text: '完全能应对', score: 0 }, { text: '有时吃力', score: 1 }, { text: '经常感到困难', score: 2 }, { text: '完全无法应对', score: 3 } ] },
        { id: 5, dimension: '压力', question: '我的身体出现以下症状：', options: [ { text: '失眠或睡眠过多', score: 1 }, { text: '头痛/胃痛等身体不适', score: 1 }, { text: '食欲显著变化', score: 1 }, { text: '无以上症状', score: 0 } ], multiple: true },
        { id: 6, dimension: '人际关系', question: '我对与他人互动感到疲惫或回避：', options: [ { text: '几乎没有', score: 0 }, { text: '偶尔', score: 1 }, { text: '经常', score: 2 }, { text: '几乎总是', score: 3 } ] },
        { id: 7, dimension: '人际关系', question: '我感到孤独或不被理解：', options: [ { text: '几乎没有', score: 0 }, { text: '偶尔', score: 1 }, { text: '经常', score: 2 }, { text: '几乎总是', score: 3 } ] },
        { id: 8, dimension: '自我认知', question: '我对自己的评价是：', options: [ { text: '总体满意', score: 0 }, { text: '有时不满意', score: 1 }, { text: '经常感到自己不够好', score: 2 }, { text: '强烈厌恶自己', score: 3 } ] },
        { id: 9, dimension: '自我认知', question: '我能感受到生活中的快乐或意义：', options: [ { text: '经常能', score: 0 }, { text: '偶尔能', score: 1 }, { text: '很少能', score: 2 }, { text: '完全不能', score: 3 } ] }
      ]
    }
  },
  computed: {
    currentQuestion() {
      return this.questions[this.currentIndex];
    },
    progress() {
      return ((this.currentIndex) / this.questions.length) * 100;
    },
    canNext() {
      const ans = this.answers[this.currentIndex];
      if (this.currentQuestion && this.currentQuestion.multiple) {
        return Array.isArray(ans) && ans.length > 0;
      }
      return ans !== undefined;
    },
    resultSummaryHtml() {
      const total = this.getTotalScore();
      if (total <= 5) {
        return `
          <p>您的总体心理状态较为稳定，情绪调节能力良好。</p>
          <div class='score-meter'><div class='score-fill' style='width: ${(total/5)*100}%'></div></div>
          <p>继续保持健康的生活习惯和社交联系，定期进行自我关怀活动。</p>
        `;
      } else if (total <= 12) {
        return `
          <p>您正在经历一些情绪困扰和压力，但仍在可调节范围内。</p>
          <div class='score-meter'><div class='score-fill' style='width: ${((total-5)/7)*100}%'></div></div>
          <p>建议关注压力源并采取积极的应对策略，必要时寻求社会支持。</p>
        `;
      } else if (total <= 20) {
        return `
          <p>您的心理压力较大，情绪困扰较为明显。</p>
          <div class='score-meter'><div class='score-fill' style='width: ${((total-12)/8)*100}%'></div></div>
          <p>建议寻求专业心理咨询或与信任的人讨论您的感受，及早干预有助于恢复。</p>
        `;
      } else {
        return `
          <p>您当前的心理困扰程度较高，可能需要专业支持。</p>
          <div class='score-meter'><div class='score-fill' style='width: 100%'></div></div>
          <p>强烈建议联系心理咨询师或精神科医生进行评估，您不必独自面对这些困难。</p>
        `;
      }
    },
    dimensionAnalysisHtml() {
      // 统计各维度分数
      const dim = { '情绪': 0, '压力': 0, '人际关系': 0, '自我认知': 0 };
      this.questions.forEach((q, i) => {
        let score = 0;
        if (q.multiple) {
          const ans = this.answers[i] || [];
          score = ans.reduce((sum, idx) => sum + (q.options[idx]?.score || 0), 0);
        } else {
          score = q.options[this.answers[i]]?.score || 0;
        }
        dim[q.dimension] += score;
      });
      let html = '';
      for (const dimension in dim) {
        const score = dim[dimension];
        let level = '';
        let description = '';
        if (score <= 1) {
          level = '低';
          description = '该维度状态良好';
        } else if (score <= 2) {
          level = '中';
          description = '该维度存在一定困扰';
        } else {
          level = '高';
          description = '该维度困扰较明显';
        }
        html += `
          <div style='margin-bottom: 15px;'>
            <h3 style='color: var(--accent-color); font-weight: 400; margin-bottom: 5px;'>${dimension}</h3>
            <div class='score-meter'><div class='score-fill' style='width: ${(score/3)*100}%'></div></div>
            <p>${level}水平 - ${description}</p>
          </div>
        `;
      }
      return html;
    },
    suggestionsHtml() {
      // 维度建议
      const dim = { '情绪': 0, '压力': 0, '人际关系': 0, '自我认知': 0 };
      this.questions.forEach((q, i) => {
        let score = 0;
        if (q.multiple) {
          const ans = this.answers[i] || [];
          score = ans.reduce((sum, idx) => sum + (q.options[idx]?.score || 0), 0);
        } else {
          score = q.options[this.answers[i]]?.score || 0;
        }
        dim[q.dimension] += score;
      });
      let html = '<div class="options">';
      for (const dimension in dim) {
        const score = dim[dimension];
        let levelKey = '';
        if (score <= 1) levelKey = 'low';
        else if (score <= 2) levelKey = 'medium';
        else levelKey = 'high';
        let dimensionKey = '';
        if (dimension === '情绪') dimensionKey = 'emotion';
        else if (dimension === '压力') dimensionKey = 'stress';
        else if (dimension === '人际关系') dimensionKey = 'relationship';
        else dimensionKey = 'self';
        const suggestion = suggestionsData[dimensionKey].find(s => s.level === levelKey);
        html += `
          <div class='question' style='margin-bottom: 15px;'>
            <h3 style='color: var(--accent-color); font-weight: 400;'>${dimension}建议</h3>
            <p>${suggestion.text}</p>
          </div>
        `;
      }
      html += '</div>';
      return html;
    },
    musicList() {
      const total = this.getTotalScore();
      if (total <= 5) return musicRecommendations.low;
      else if (total <= 12) return musicRecommendations.medium;
      else return musicRecommendations.high;
    }
  },
  methods: {
    startTest() {
      this.step = 'test';
      this.currentIndex = 0;
      this.answers = [];
      if (this.questions[0].multiple) this.$set(this.answers, 0, []);
    },
    nextQuestion() {
      if (this.currentIndex < this.questions.length - 1) {
        this.currentIndex++;
        if (this.currentQuestion.multiple && !Array.isArray(this.answers[this.currentIndex])) {
          this.$set(this.answers, this.currentIndex, []);
        }
      } else {
        this.step = 'result';
      }
    },
    restart() {
      this.step = 'intro';
      this.currentIndex = 0;
      this.answers = [];
    },
    getTotalScore() {
      let total = 0;
      this.questions.forEach((q, i) => {
        if (q.multiple) {
          const ans = this.answers[i] || [];
          total += ans.reduce((sum, idx) => sum + (q.options[idx]?.score || 0), 0);
        } else {
          total += q.options[this.answers[i]]?.score || 0;
        }
      });
      return total;
    }
  }
}
</script>

<template>
  <div class="container">
    <header>
      <div class="flower-decoration">🌷</div>
      <h1>心灵花园</h1>
      <p class="subtitle">一份温暖的心理自评测试</p>
    </header>
    <div class="intro-container" :class="{ active: step === 'intro' }">
      <div class="question">
        <h3>欢迎来到心灵花园</h3>
        <p>这是一份专业的心理自评测试，包含情绪、压力、人际关系和自我认知四个维度。</p>
        <p>测试完成后，您将获得：</p>
        <ul style="margin: 15px 0 15px 20px;">
          <li>多维度的心理状态评估</li>
          <li>个性化的改善建议</li>
          <li>精选的治愈系音乐推荐</li>
        </ul>
        <p>请根据最近两周的真实感受回答，答案没有对错之分。</p>
      </div>
      <button class="btn" @click="startTest">开始测试</button>
    </div>
    <div class="test-container" :class="{ active: step === 'test' }">
      <div class="progress-container">
        <div class="progress-bar" :style="{ width: progress + '%' }"></div>
      </div>
      <div id="questionContainer">
        <div v-if="currentQuestion" class="question">
          <h3>{{ currentQuestion.dimension }}维度</h3>
          <p>{{ currentQuestion.question }}</p>
          <div class="options">
            <template v-if="!currentQuestion.multiple">
              <label v-for="(opt, idx) in currentQuestion.options" :key="idx" class="option">
                <input type="radio" :name="'q' + currentQuestion.id" :value="idx" v-model="answers[currentIndex]">
                {{ opt.text }}
              </label>
            </template>
            <template v-else>
              <label v-for="(opt, idx) in currentQuestion.options" :key="idx" class="option">
                <input type="checkbox" :value="idx" v-model="answers[currentIndex]">
                {{ opt.text }}
              </label>
            </template>
          </div>
        </div>
      </div>
      <button class="btn" id="nextBtn" :disabled="!canNext" @click="nextQuestion">{{ currentIndex === questions.length - 1 ? '提交' : '下一题' }}</button>
    </div>
    <div class="result-container" :class="{ active: step === 'result' }">
      <div class="flower-decoration">🌼</div>
      <div class="result-section">
        <h2>您的心理状态评估</h2>
        <div id="resultSummary" v-html="resultSummaryHtml"></div>
      </div>
      <div class="result-section">
        <h2>各维度详细分析</h2>
        <div id="dimensionAnalysis" v-html="dimensionAnalysisHtml"></div>
      </div>
      <div class="result-section">
        <h2>个性化建议</h2>
        <div id="suggestions" v-html="suggestionsHtml"></div>
      </div>
      <div class="result-section">
        <h2>为您推荐的音乐</h2>
        <p>根据您的测试结果，这些音乐可能有助于缓解您的情绪：</p>
        <div id="musicRecommendations">
          <div v-for="(music, idx) in musicList" :key="idx" class="music-card">
            <div class="music-cover" :style="{ backgroundImage: 'url(' + music.cover + ')' }"></div>
            <div class="music-info">
              <div class="music-title">{{ music.title }}</div>
              <div class="music-reason">{{ music.reason }}</div>
            </div>
          </div>
        </div>
      </div>
      <button class="btn restart-btn" id="restartBtn" @click="restart">重新测试</button>
    </div>
  </div>
</template>

<style>
:root {
  --primary-color: #a8d8ea;
  --secondary-color: #56dad7;
  --accent-color: #50ade7;
  --text-color: #555;
  --light-color: #fef6e4;
}
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: 'Helvetica Neue', Arial, sans-serif;
}
body {
  background-color: var(--light-color);
  color: var(--text-color);
  line-height: 1.6;
  padding: 20px;
  background-image: url('https://images.unsplash.com/photo-1490750967868-88aa4486c946?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80');
  background-size: cover;
  background-attachment: fixed;
  background-position: center;
  background-blend-mode: overlay;
  background-color: rgba(255, 255, 255, 0.85);
}
.container {
  max-width: 800px;
  margin: 0 auto;
  background-color: rgba(255, 255, 255, 0.9);
  border-radius: 15px;
  padding: 30px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(5px);
}
header {
  text-align: center;
  margin-bottom: 30px;
}
h1 {
  color: var(--accent-color);
  font-size: 2.5rem;
  margin-bottom: 10px;
  font-weight: 300;
}
.subtitle {
  color: var(--text-color);
  font-size: 1.1rem;
  font-weight: 300;
}
.test-container, .result-container {
  display: none;
  animation: fadeIn 0.5s ease;
}
.active {
  display: block;
}
.question {
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  margin-bottom: 20px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
  border-left: 5px solid var(--primary-color);
}
.question h3 {
  color: var(--accent-color);
  margin-bottom: 15px;
  font-weight: 400;
}
.options {
  display: flex;
  flex-direction: column;
  gap: 10px;
}
.option {
  display: flex;
  align-items: center;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
  transition: all 0.3s ease;
}
.option:hover {
  background-color: var(--primary-color);
  transform: translateX(5px);
}
.option input {
  margin-right: 10px;
}
.btn {
  background-color: var(--accent-color);
  color: white;
  border: none;
  padding: 12px 25px;
  border-radius: 30px;
  cursor: pointer;
  font-size: 1rem;
  transition: all 0.3s ease;
  display: block;
  margin: 30px auto 0;
  box-shadow: 0 5px 15px rgba(170, 150, 218, 0.3);
}
.btn:hover {
  background-color: #f7f7f7;
  color: var(--accent-color);
  transform: translateY(-2px);
  box-shadow: 0 7px 20px rgba(170, 150, 218, 0.4);
}
.btn:disabled {
  background-color: #ccc;
  cursor: not-allowed;
  transform: none;
  box-shadow: none;
}
.progress-container {
  width: 100%;
  background-color: #f0f0f0;
  border-radius: 10px;
  margin-bottom: 30px;
  height: 10px;
}
.progress-bar {
  height: 100%;
  border-radius: 10px;
  background: linear-gradient(90deg, var(--primary-color), var(--accent-color));
  width: 0%;
  transition: width 0.5s ease;
}
.result-section {
  margin-bottom: 30px;
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
}
.result-section h2 {
  color: var(--accent-color);
  margin-bottom: 15px;
  font-weight: 400;
  border-bottom: 1px solid #eee;
  padding-bottom: 10px;
}
.score-meter {
  height: 20px;
  background-color: #f0f0f0;
  border-radius: 10px;
  margin: 15px 0;
  overflow: hidden;
}
.score-fill {
  height: 100%;
  border-radius: 10px;
  background: linear-gradient(90deg, var(--primary-color), var(--accent-color));
}
.music-card {
  display: flex;
  background-color: white;
  border-radius: 10px;
  overflow: hidden;
  margin-bottom: 15px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
}
.music-cover {
  width: 80px;
  height: 80px;
  background-size: cover;
  background-position: center;
}
.music-info {
  padding: 15px;
  flex: 1;
}
.music-title {
  font-weight: bold;
  margin-bottom: 5px;
  color: var(--accent-color);
}
.music-reason {
  font-size: 0.9rem;
  color: #777;
}
.restart-btn {
  background-color: white;
  color: var(--accent-color);
  border: 1px solid var(--accent-color);
}
.restart-btn:hover {
  background-color: var(--accent-color);
  color: white;
}
@keyframes fadeIn {
  from { opacity: 0; transform: translateY(20px); }
  to { opacity: 1; transform: translateY(0); }
}
.flower-decoration {
  text-align: center;
  margin: 20px 0;
  font-size: 1.5rem;
  color: var(--accent-color);
}
@media (max-width: 600px) {
  .container {
    padding: 20px;
  }
  h1 {
    font-size: 2rem;
  }
  .music-card {
    flex-direction: column;
  }
  .music-cover {
    width: 100%;
    height: 120px;
  }
}
</style>
<script>
export default {
  name: 'MoodCalendar',
  data() {
    const today = new Date();
    return {
      year: today.getFullYear(),
      month: today.getMonth(),
      moodData: {},
      noteData: {},
      currentSelectedDate: null,
      showMoodPopup: false,
      showNotePopup: false,
      showStatsPopup: false,
      showOptionPopup: false,
      showToast: false,
      toastMessage: '',
      noteInput: '',
      showEmojiPicker: false,
      weekDays: ['日', '一', '二', '三', '四', '五', '六'],
      moodOptions: [
        { emoji: '😀', label: '开心' },
        { emoji: '😐', label: '平淡' },
        { emoji: '😢', label: '难过' }
      ],
      emojis: ['😀', '😐', '😢', '😎', '🥳']
    }
  },
  computed: {
    calendarDays() {
      const firstDay = new Date(this.year, this.month, 1).getDay();
      const lastDay = new Date(this.year, this.month + 1, 0).getDate();
      const days = [];
      for (let i = 0; i < firstDay; i++) days.push(null);
      for (let d = 1; d <= lastDay; d++) {
        const key = `${this.year}-${this.month + 1}-${d}`;
        days.push({ date: d, key });
      }
      return days;
    },
    happyCount() {
      return Object.entries(this.moodData).filter(([k, v]) => v === '😀' && k.startsWith(`${this.year}-${this.month + 1}`)).length;
    },
    neutralCount() {
      return Object.entries(this.moodData).filter(([k, v]) => v === '😐' && k.startsWith(`${this.year}-${this.month + 1}`)).length;
    },
    sadCount() {
      return Object.entries(this.moodData).filter(([k, v]) => v === '😢' && k.startsWith(`${this.year}-${this.month + 1}`)).length;
    }
  },
  methods: {
    prevMonth() {
      this.month--;
      if (this.month < 0) {
        this.month = 11;
        this.year--;
      }
    },
    nextMonth() {
      this.month++;
      if (this.month > 11) {
        this.month = 0;
        this.year++;
      }
    },
    selectDay(day, e) {
      if (!day) return;
      this.currentSelectedDate = day.key;
      // 取消所有选中
      const days = e.target.parentNode.querySelectorAll('.day');
      days.forEach(d => d.classList.remove('selected'));
      e.currentTarget.classList.add('selected');
      this.showOptionPopup = true;
    },
    chooseMood() {
      this.showOptionPopup = false;
      this.showMoodPopup = true;
    },
    addNote() {
      this.showOptionPopup = false;
      this.noteInput = this.noteData[this.currentSelectedDate] || '';
      this.showNotePopup = true;
    },
    setMood(emoji) {
      if (this.currentSelectedDate) {
        this.$set(this.moodData, this.currentSelectedDate, emoji);
        this.showMoodPopup = false;
        this.noteInput = this.noteData[this.currentSelectedDate] || '';
        this.showNotePopup = true;
      }
    },
    saveNote() {
      if (this.currentSelectedDate) {
        this.$set(this.noteData, this.currentSelectedDate, this.noteInput);
        this.showNotePopup = false;
        this.showToastMsg('备注保存成功');
      }
    },
    cancelNote() {
      this.showNotePopup = false;
    },
    showStats() {
      this.showStatsPopup = true;
    },
    exitStats() {
      this.showStatsPopup = false;
    },
    resetCalendar() {
      const currentMonthKey = `${this.year}-${this.month + 1}`;
      Object.keys(this.moodData).forEach(key => {
        if (key.startsWith(currentMonthKey)) {
          this.$delete(this.moodData, key);
          this.$delete(this.noteData, key);
        }
      });
      this.showToastMsg('当前月份日历已重置');
    },
    goToProfile() {
      this.$router.push('/personal-page');
    },
    showToastMsg(msg) {
      this.toastMessage = msg;
      this.showToast = true;
      setTimeout(() => { this.showToast = false; }, 2000);
    },
    toggleEmojiPicker() {
      this.showEmojiPicker = !this.showEmojiPicker;
    },
    addEmoji(emoji) {
      this.noteInput += emoji;
      this.showEmojiPicker = false;
    },
    exitOption() {
      this.showOptionPopup = false;
    },
    exitMood() {
      this.showMoodPopup = false;
    }
  }
}
</script>

<template>
  <div class="font-sans bg-blue-50 min-h-screen">
    <header class="header p-6 relative">
      <div class="title-row">
        <i class="fa-solid fa-heart text-4xl mr-3"></i>
        <h1 class="text-3xl font-bold font-serif m-0">心情日历</h1>
      </div>
      <p class="text-center mt-2 opacity-90">记录每一天的心情点滴</p>
      <button class="user-btn" title="个人中心" @click="goToProfile">
        <i class="fas fa-user"></i>
      </button>
    </header>
    <main class="p-6">
      <div class="flex justify-between mb-4">
        <button class="button px-4 py-2 rounded" @click="prevMonth">
          <i class="fa-solid fa-chevron-left"></i>
        </button>
        <h2 class="text-xl font-bold">{{ year }}年 {{ month + 1 }}月</h2>
        <button class="button px-4 py-2 rounded" @click="nextMonth">
          <i class="fa-solid fa-chevron-right"></i>
        </button>
        <button class="button px-4 py-2 rounded" @click="showStats()">
          查看统计
        </button>
        <button class="button px-4 py-2 rounded" @click="resetCalendar">
          重置日历
        </button>
      </div>
      <div class="calendar">
        <div class="flex border-b border-gray-300">
          <div v-for="w in weekDays" :key="w">{{ w }}</div>
        </div>
        <div class="flex flex-wrap" style="min-height: 280px;">
          <div v-for="(day, idx) in calendarDays" :key="idx" class="day" :class="{ selected: currentSelectedDate === (day && day.key) }" @click="selectDay(day, $event)">
            <div>{{ day && day.date }}</div>
            <i v-if="day && moodData[day.key]" class="mood-icon">{{ moodData[day.key] }}</i>
            <i v-if="day && noteData[day.key]" class="fa-solid fa-note-sticky ml-1 text-gray-500"></i>
          </div>
        </div>
      </div>
    </main>
    <!-- 操作选项弹窗 -->
    <div class="option-popup" v-show="showOptionPopup">
      <h3 class="text-lg font-bold mb-4">操作选项</h3>
      <button class="button px-4 py-2 rounded mb-2" @click="chooseMood">选择心情</button>
      <button class="button px-4 py-2 rounded" @click="addNote">添加备注</button>
      <button class="button px-4 py-2 rounded mt-4" @click="exitOption">退出</button>
    </div>
    <!-- 心情选择弹窗 -->
    <div class="mood-popup" v-show="showMoodPopup">
      <h3 class="text-lg font-bold mb-4">选择心情</h3>
      <div class="flex justify-center">
        <div class="mood-option" v-for="m in moodOptions" :key="m.emoji" @click="setMood(m.emoji)">
          <i class="mood-icon">{{ m.emoji }}</i>
          <p>{{ m.label }}</p>
        </div>
      </div>
      <button class="button px-4 py-2 rounded mt-4" @click="exitMood">退出</button>
    </div>
    <!-- 备注弹窗 -->
    <div class="note-popup" v-show="showNotePopup">
      <h3 class="text-lg font-bold mb-4">添加心情备注</h3>
      <textarea v-model="noteInput" class="w-full border border-gray-300 p-2 mb-2" rows="3"></textarea>
      <button class="button px-2 py-1 rounded" @click="toggleEmojiPicker">😀</button>
      <div class="emoji-picker" v-show="showEmojiPicker">
        <span class="emoji" v-for="e in emojis" :key="e" @click="addEmoji(e)">{{ e }}</span>
      </div>
      <button class="button px-4 py-2 rounded" @click="saveNote">保存备注</button>
      <button class="button px-4 py-2 rounded ml-2" @click="cancelNote">取消</button>
    </div>
    <!-- 统计弹窗 -->
    <div class="stats-popup" v-show="showStatsPopup">
      <h3 class="text-lg font-bold mb-4">心情统计</h3>
      <p>开心天数: {{ happyCount }}</p>
      <p>平淡天数: {{ neutralCount }}</p>
      <p>难过天数: {{ sadCount }}</p>
      <button class="button px-4 py-2 rounded mt-4" @click="exitStats">退出</button>
    </div>
    <!-- 提示 -->
    <div class="toast" :class="{ show: showToast }">{{ toastMessage }}</div>
  </div>
</template>

<style>
body {
  font-family: 'Inter', sans-serif;
  background-color: #e6f7ff;
}
.header {
  background: linear-gradient(135deg, #93d5f2 0%, #76cff8 100%);
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
  padding: 2rem 0 2rem 0;
  text-align: center;
  position: relative;
}
.title-row {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.75rem;
}
.header h1 {
  color: #fff;
}
.header p {
  color: #fff;
}
.calendar {
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
}
.calendar .flex:first-child > div {
  width: 14.28%;
  text-align: center;
  padding: 5px;
}
.day {
  width: 14.28%;
  text-align: center;
  padding: 10px;
  cursor: pointer;
  transition: background-color 0.2s ease-in-out;
}
.day.selected {
  background-color: #b2e1f0;
}
.day:hover {
  background-color: #e6f9f2;
}
.mood-icon {
  font-size: 24px;
}
.mood-popup, .stats-popup, .note-popup, .option-popup {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
  z-index: 1000;
}
.mood-option {
  cursor: pointer;
  margin: 10px;
  text-align: center;
}
.emoji-picker {
  position: absolute;
  bottom: 100%;
  left: 0;
  background-color: white;
  border: 1px solid #ccc;
  border-radius: 5px;
  padding: 10px;
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 5px;
}
.emoji {
  cursor: pointer;
  font-size: 20px;
}
.toast {
  position: fixed;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%);
  background-color: rgba(0, 0, 0, 0.8);
  color: white;
  padding: 10px 20px;
  border-radius: 5px;
  opacity: 0;
  transition: opacity 0.3s ease-in-out;
  z-index: 2000;
}
.toast.show {
  opacity: 1;
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
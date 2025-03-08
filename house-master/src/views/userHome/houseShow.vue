<template>
  <div>
    <!-- 查询房源 -->
    <div class="filter-bar">
      <input v-model="filters.community" type="text" placeholder="输入小区名" />
      <select v-model="filters.statue">
        <option value="">全部</option>
        <option value=0>待租</option>
        <option value=1>已出租</option>
      </select>
      <input v-model.number="filters.minPrice" type="number" placeholder="最低价格" class="small-input" />
      <input v-model.number="filters.maxPrice" type="number" placeholder="最高价格" class="small-input" />
      <input v-model.number="filters.minFloor" type="number" placeholder="最低楼层" class="small-input" />
      <input v-model.number="filters.maxFloor" type="number" placeholder="最高楼层" class="small-input" />
      <input v-model.number="filters.minArea" type="number" placeholder="最小面积" class="small-input" />
      <input v-model.number="filters.maxArea" type="number" placeholder="最大面积" class="small-input" />
      <button @click="applyFilters">
        <i class="el-icon-search"></i>
        查询
      </button>
    </div>

    <!-- 房源列表 -->
    <div class="content">
      <div class="card" v-for="house in filteredHouses" :key="house.houseid" @click="goToDetail(house.houseid)">
        <img :src="'/images/' + house.image" alt="房屋图片" />
        <div class="card-body">
          <span class="status" :class="statusClass(house)">
            {{ statusText(house) }}
          </span>
          <p>{{ house.community }}<span></span>￥{{ house.price }} / 月</p>
          <p>面积：{{ house.area }}/m² 楼层：{{house.floor}}</p>
          <p>房型：{{ house.type }}</p>
          <p>地址：{{ house.address }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
export default {
  data() {
    return {
      user: {
        username: "",
      },
      houses: [
        {
          houseid: 1,
          statue: 0,
          area: 20,
          floor: 1,
          price: 2370,
          community: '华策中心城',
          address: '三号线 吉祥站 花园小区',
          type: '一室一厅',
          toward: '正南朝向',
          surrounding: '近地铁',
          decoration: '精装修',
          housename: '张三',
          housephone: '13812345678',
          tenantname: '李四',
          image: '',
        },
      ],
      filters: {
        community: "",
        statue: "",
        minPrice: null,
        maxPrice: null,
        minFloor: null,
        maxFloor: null,
        minArea: null,
        maxArea: null,
      },
    };
  },
  computed: {
    filteredHouses() {
      return this.houses.filter((house) => {
        const matchesCommunity =
          !this.filters.community ||
          house.community.includes(this.filters.community);
        const matchesStatue =
          this.filters.statue === "" ||
          house.statue === Number(this.filters.statue);
        const matchesPrice =
          (this.filters.minPrice === null || house.price >= this.filters.minPrice) &&
          (this.filters.maxPrice === null || house.price <= this.filters.maxPrice);
        const matchesFloor =
          (this.filters.minFloor === null || house.floor >= this.filters.minFloor) &&
          (this.filters.maxFloor === null || house.floor <= this.filters.maxFloor);
        const matchesArea =
          (this.filters.minArea === null || house.area >= this.filters.minArea) &&
          (this.filters.maxArea === null || house.area <= this.filters.maxArea);
        return matchesCommunity && matchesStatue && matchesPrice && matchesFloor && matchesArea;
      });
    },
    statusClass() {
      return (house) => {
        if (house.statue === 0) return "notRented";
        if (house.statue === 1 && this.user.username === house.tenantname)
          return "myHouse";
        if (house.statue === 1) return "rented";
        return "";
      };
    },
    statusText() {
      return (house) => {
        if (house.statue === 0) return "待租";
        if (house.statue === 1 && this.user.username === house.tenantname)
          return "本人居住";
        if (house.statue === 1) return "已出租";
        return "";
      };
    },
  },
  created() {
    this.getToken();
    // this.getHouseAll();
    this.applyFilters();
    
  },
  methods: {
    getToken() {
      const token = localStorage.getItem("token");
      this.user = JSON.parse(token);
      if (this.user == null) {
        router.push("/login");
      }
    },
    
    // 查询用户的房子-每次进入页面都会调用
    getHouseAll() {
      request({
        method: "get",
        url: "/userHome/houseShow",
      }).then((response) => {
        this.houses = response.data.data;
      });
    },
    // 跳转到房源详情页
    goToDetail(houseid) {
      this.$router.push({ path: `userhouse/detail/${houseid}` });
    },
    // 应用查询条件
    applyFilters() {
      // 手动触发更新，实际是对 filteredHouses 依赖的 filters 进行了更改
      request({
        method: "get",
        url: `/userHome/houseShow/community?_=${Date.now()}`,
        params:{
          community: this.filters.community
        }
      }).then((response) => {
        console.log(response.data.data);
        this.houses = response.data.data;
      });
    },
  },
};
</script>

<style>

.status {
  background-color: green;
  color: white;
  padding: 3px 3px;
  border-radius: 5px;
  font-size: 13px;
  position: absolute;
  line-height: 20px;
  top: 10px;
  right: 10px;
  z-index: 1; /* 确保显示在卡片顶部 */
}
/* 未出租 */
.status.rented {
  background-color: red;
}
/* 我租的房子 */
.status.myHouse {
  background-color: orange;
}



.filter-bar {
  margin-bottom: 20px;
  display: flex;
  gap: 10px;
}

.filter-bar input,
.filter-bar select {
  padding: 5px 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
}

.filter-bar .small-input {
  width: 100px;
}

.filter-bar button {
  padding: 5px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.filter-bar button:hover {
  background-color: #0056b3;
}
</style>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useAuthStore } from '../stores/auth';

const authStore = useAuthStore();
const agendas = ref([]);
const announcements = ref([]);

const API_AGENDAS = import.meta.env.VITE_API_BASE_URL.replace('/auth/', '/agendas');
const API_ANNOUNCEMENTS = import.meta.env.VITE_API_BASE_URL.replace('/auth/', '/announcements');

const fetchData = async () => {
    try {
        const [projectsRes, announcementsRes] = await Promise.all([
            axios.get(API_AGENDAS, { headers: { Authorization: 'Bearer ' + authStore.token } }),
            axios.get(API_ANNOUNCEMENTS, { headers: { Authorization: 'Bearer ' + authStore.token } })
        ]);
        // Take top 3
        agendas.value = projectsRes.data.slice(0, 3);
        announcements.value = announcementsRes.data.slice(0, 3);
    } catch (err) {
        console.error("Failed to fetch dashboard data", err);
    }
};

onMounted(() => {
    fetchData();
});
</script>

<template>
  <div class="px-4 py-6 sm:px-0 space-y-8">
    
    <!-- Stats Row -->
    <div class="grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-3">
      <!-- Welcome Card -->
      <div class="bg-white overflow-hidden shadow rounded-lg">
        <div class="p-5">
          <div class="flex items-center">
            <div class="flex-shrink-0 bg-indigo-500 rounded-md p-3">
              <svg class="h-6 w-6 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6" />
              </svg>
            </div>
            <div class="ml-5 w-0 flex-1">
              <dl>
                <dt class="text-sm font-medium text-gray-500 truncate">Welcome back</dt>
                <dd>
                  <div class="text-lg font-medium text-gray-900">{{ authStore.user?.username }}</div>
                </dd>
              </dl>
            </div>
          </div>
        </div>
        <div class="bg-gray-50 px-5 py-3">
          <div class="text-sm">
            <span class="font-medium text-indigo-700">Role: {{ authStore.user?.role }}</span>
          </div>
        </div>
      </div>
    
       <!-- Upcoming Agendas Summary -->
      <div class="bg-white overflow-hidden shadow rounded-lg sm:col-span-1 lg:col-span-1">
        <div class="p-5">
            <h3 class="text-lg leading-6 font-medium text-gray-900 mb-4">Upcoming Agendas</h3>
            <ul class="divide-y divide-gray-200">
                <li v-for="agenda in agendas" :key="agenda.id" class="py-3">
                    <div class="flex items-center space-x-3">
                        <div class="flex-shrink-0">
                            <span class="inline-flex items-center justify-center h-8 w-8 rounded-full bg-blue-100 text-blue-600 font-semibold text-xs">
                                {{ new Date(agenda.eventDate).getDate() }}
                            </span>
                        </div>
                        <div class="min-w-0 flex-1">
                            <p class="text-sm font-medium text-gray-900 truncate">{{ agenda.title }}</p>
                            <p class="text-sm text-gray-500 truncate">{{ new Date(agenda.eventDate).toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'}) }}</p>
                        </div>
                    </div>
                </li>
                 <li v-if="agendas.length === 0" class="py-3 text-sm text-gray-500">No upcoming agendas.</li>
            </ul>
        </div>
          <div class="bg-gray-50 px-5 py-3">
            <div class="text-sm">
                <router-link to="/agendas" class="font-medium text-indigo-700 hover:text-indigo-900">View all agendas</router-link>
            </div>
        </div>
      </div>

       <!-- Recent Announcements Summary -->
      <div class="bg-white overflow-hidden shadow rounded-lg sm:col-span-1 lg:col-span-1">
        <div class="p-5">
             <h3 class="text-lg leading-6 font-medium text-gray-900 mb-4">Latest Announcements</h3>
             <ul class="divide-y divide-gray-200">
                <li v-for="announcement in announcements" :key="announcement.id" class="py-3">
                    <div class="min-w-0 flex-1">
                        <p class="text-sm font-medium text-gray-900 truncate">{{ announcement.title }}</p>
                        <p class="text-xs text-gray-500 line-clamp-2">{{ announcement.content }}</p>
                         <p class="text-xs text-gray-400 mt-1">{{ new Date(announcement.createdAt).toLocaleDateString() }}</p>
                    </div>
                </li>
                 <li v-if="announcements.length === 0" class="py-3 text-sm text-gray-500">No recent announcements.</li>
            </ul>
        </div>
        <div class="bg-gray-50 px-5 py-3">
            <div class="text-sm">
                <router-link to="/announcements" class="font-medium text-indigo-700 hover:text-indigo-900">View all announcements</router-link>
            </div>
        </div>
      </div>
    
    </div>
  </div>
</template>

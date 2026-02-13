<script setup>
import { ref, onMounted, computed } from 'vue';
import axios from 'axios';
import { useAuthStore } from '../stores/auth';

const authStore = useAuthStore();
const agendas = ref([]);
const newAgenda = ref({ title: '', description: '', eventDate: '', targetAudience: 'ALL' });
const message = ref('');
const error = ref('');
const searchQuery = ref('');

const API_URL = import.meta.env.VITE_API_BASE_URL.replace('/auth/', '/agendas');

const fetchAgendas = async () => {
    try {
        const response = await axios.get(API_URL, {
            headers: { Authorization: 'Bearer ' + authStore.token }
        });
        agendas.value = response.data;
    } catch (err) {
        error.value = 'Failed to fetch agendas';
    }
};

const createAgenda = async () => {
    try {
        message.value = '';
        error.value = '';
        await axios.post(API_URL, newAgenda.value, {
            headers: { Authorization: 'Bearer ' + authStore.token }
        });
        message.value = 'Agenda created successfully!';
        newAgenda.value = { title: '', description: '', eventDate: '', targetAudience: 'ALL' };
        fetchAgendas();
    } catch (err) {
        error.value = err.response?.data || 'Failed to create agenda';
    }
};

const deleteAgenda = async (id) => {
    if (!confirm('Are you sure you want to delete this agenda?')) return;
    try {
        await axios.delete(`${API_URL}/${id}`, {
            headers: { Authorization: 'Bearer ' + authStore.token }
        });
        fetchAgendas();
    } catch (err) {
        error.value = 'Failed to delete agenda';
    }
};

const filteredAgendas = computed(() => {
    return agendas.value.filter(agenda => 
        agenda.title.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
        agenda.description.toLowerCase().includes(searchQuery.value.toLowerCase())
    );
});

const canManage = computed(() => {
    return ['ADMIN', 'OPERATOR', 'TEACHER'].includes(authStore.user?.role);
});

onMounted(() => {
    fetchAgendas();
});
</script>

<template>
  <div class="space-y-6">
    <div class="flex justify-between items-center">
        <h2 class="text-2xl font-bold text-gray-800">Agendas & Events</h2>
    </div>

    <div v-if="message" class="p-4 bg-green-100 border-l-4 border-green-500 text-green-700 rounded shadow-sm">{{ message }}</div>
    <div v-if="error" class="p-4 bg-red-100 border-l-4 border-red-500 text-red-700 rounded shadow-sm">{{ error }}</div>

    <!-- Create Agenda Card -->
    <div v-if="canManage" class="bg-white shadow-lg rounded-xl overflow-hidden">
        <div class="bg-indigo-50 px-6 py-4 border-b border-indigo-100">
             <h3 class="text-lg font-semibold text-indigo-900">Add New Agenda</h3>
        </div>
        <div class="p-6">
            <form @submit.prevent="createAgenda" class="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-5 items-end">
                <div class="lg:col-span-2">
                    <label class="block text-sm font-medium text-gray-700 mb-1">Title</label>
                    <input v-model="newAgenda.title" type="text" required class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border p-2" />
                </div>
                <div class="lg:col-span-3">
                    <label class="block text-sm font-medium text-gray-700 mb-1">Description</label>
                    <input v-model="newAgenda.description" type="text" class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border p-2" />
                </div>
                <div>
                     <label class="block text-sm font-medium text-gray-700 mb-1">Event Date</label>
                     <input v-model="newAgenda.eventDate" type="datetime-local" required class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border p-2" />
                </div>
                 <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">Target Audience</label>
                    <select v-model="newAgenda.targetAudience" class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border p-2">
                        <option value="ALL">All</option>
                        <option value="TEACHERS">Teachers</option>
                        <option value="STUDENTS">Students</option>
                    </select>
                </div>
                <div>
                     <button type="submit" class="w-full inline-flex justify-center rounded-md border border-transparent bg-indigo-600 py-2 px-4 text-sm font-medium text-white shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 transition-colors">
                        Add Agenda
                    </button>
                </div>
            </form>
        </div>
    </div>

    <!-- Agenda List Card -->
    <div class="bg-white shadow-lg rounded-xl overflow-hidden">
        <div class="px-6 py-4 border-b border-gray-200 flex justify-between items-center bg-gray-50">
            <h3 class="text-lg font-semibold text-gray-800">Upcoming Agendas</h3>
            <div class="relative">
                 <input v-model="searchQuery" type="text" placeholder="Search agendas..." class="pl-10 pr-4 py-2 border border-gray-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent" />
                 <div class="absolute left-3 top-2.5 text-gray-400">
                     <svg class="h-4 w-4" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                        <path fill-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" clip-rule="evenodd" />
                    </svg>
                </div>
            </div>
        </div>

        <div class="overflow-x-auto">
            <table class="min-w-full divide-y divide-gray-200">
                <thead class="bg-gray-50">
                    <tr>
                        <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Date</th>
                        <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Title</th>
                        <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Audience</th>
                         <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Author</th>
                        <th v-if="canManage" scope="col" class="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
                    </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                    <tr v-for="agenda in filteredAgendas" :key="agenda.id" class="hover:bg-gray-50 transition-colors">
                        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                            {{ new Date(agenda.eventDate).toLocaleDateString() }} {{ new Date(agenda.eventDate).toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'}) }}
                        </td>
                        <td class="px-6 py-4 text-sm font-medium text-gray-900">
                            {{ agenda.title }}
                            <p class="text-xs text-gray-500 font-normal">{{ agenda.description }}</p>
                        </td>
                         <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                             <span :class="{'bg-green-100 text-green-800': agenda.targetAudience === 'ALL', 'bg-blue-100 text-blue-800': agenda.targetAudience === 'STUDENTS', 'bg-yellow-100 text-yellow-800': agenda.targetAudience === 'TEACHERS'}" class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full">
                                {{ agenda.targetAudience }}
                            </span>
                        </td>
                        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ agenda.author?.username }}</td>
                        <td v-if="canManage" class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                            <button @click="deleteAgenda(agenda.id)" class="text-red-400 hover:text-red-600 transition-colors">
                                <svg class="h-5 w-5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                                    <path fill-rule="evenodd" d="M9 2a1 1 0 00-.894.553L7.382 4H4a1 1 0 000 2v10a2 2 0 002 2h8a2 2 0 002-2V6a1 1 0 100-2h-3.382l-.724-1.447A1 1 0 0011 2H9zM7 8a1 1 0 012 0v6a1 1 0 11-2 0V8zm5-1a1 1 0 00-1 1v6a1 1 0 102 0V8a1 1 0 00-1-1z" clip-rule="evenodd" />
                                </svg>
                            </button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
  </div>
</template>

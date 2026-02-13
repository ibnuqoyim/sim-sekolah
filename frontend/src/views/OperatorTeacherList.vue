<script setup>
import { ref, onMounted, computed } from 'vue';
import axios from 'axios';
import { useAuthStore } from '../stores/auth';

const authStore = useAuthStore();
const teachers = ref([]);
const newTeacher = ref({ username: '', email: '', password: '', bio: '', subjects: '' });
const message = ref('');
const error = ref('');
const searchQuery = ref('');

const API_URL = import.meta.env.VITE_API_BASE_URL.replace('/auth/', '/teachers');

const fetchTeachers = async () => {
    try {
        const response = await axios.get(API_URL, {
            headers: { Authorization: 'Bearer ' + authStore.token }
        });
        teachers.value = response.data;
    } catch (err) {
        error.value = 'Failed to fetch teachers';
    }
};

const createTeacher = async () => {
    try {
        message.value = '';
        error.value = '';
        await axios.post(API_URL, newTeacher.value, {
            headers: { Authorization: 'Bearer ' + authStore.token }
        });
        message.value = 'Teacher created successfully!';
        newTeacher.value = { username: '', email: '', password: '', bio: '', subjects: '' };
        fetchTeachers();
    } catch (err) {
        error.value = err.response?.data || 'Failed to create teacher';
    }
};

const deleteTeacher = async (id) => {
    if (!confirm('Are you sure you want to delete this teacher?')) return;
    try {
        await axios.delete(`${API_URL}/${id}`, {
            headers: { Authorization: 'Bearer ' + authStore.token }
        });
        fetchTeachers();
    } catch (err) {
        error.value = 'Failed to delete teacher';
    }
};

const filteredTeachers = computed(() => {
    return teachers.value.filter(teacher => 
        teacher.user.username.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
        teacher.subjects.toLowerCase().includes(searchQuery.value.toLowerCase())
    );
});

onMounted(() => {
    fetchTeachers();
});
</script>

<template>
  <div class="space-y-6">
    <div class="flex justify-between items-center">
        <h2 class="text-2xl font-bold text-gray-800">Teacher Management</h2>
    </div>

    <div v-if="message" class="p-4 bg-green-100 border-l-4 border-green-500 text-green-700 rounded shadow-sm">{{ message }}</div>
    <div v-if="error" class="p-4 bg-red-100 border-l-4 border-red-500 text-red-700 rounded shadow-sm">{{ error }}</div>

    <!-- Create Teacher Card -->
    <div class="bg-white shadow-lg rounded-xl overflow-hidden">
        <div class="bg-indigo-50 px-6 py-4 border-b border-indigo-100">
             <h3 class="text-lg font-semibold text-indigo-900">Add New Teacher</h3>
        </div>
        <div class="p-6">
            <form @submit.prevent="createTeacher" class="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3 items-end">
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">Username</label>
                    <input v-model="newTeacher.username" type="text" required class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border p-2" />
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">Email</label>
                    <input v-model="newTeacher.email" type="email" required class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border p-2" />
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">Password</label>
                    <input v-model="newTeacher.password" type="password" required class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border p-2" />
                </div>
                <div class="lg:col-span-2">
                    <label class="block text-sm font-medium text-gray-700 mb-1">Bio</label>
                    <textarea v-model="newTeacher.bio" rows="1" class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border p-2"></textarea>
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">Subjects</label>
                    <input v-model="newTeacher.subjects" type="text" placeholder="e.g. Math, Physics" class="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border p-2" />
                </div>
                <div class="lg:col-span-3 flex justify-end">
                    <button type="submit" class="inline-flex justify-center rounded-md border border-transparent bg-indigo-600 py-2 px-6 text-sm font-medium text-white shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 transition-colors">
                        Add Teacher
                    </button>
                </div>
            </form>
        </div>
    </div>

    <!-- Teacher List Card -->
    <div class="bg-white shadow-lg rounded-xl overflow-hidden">
        <div class="px-6 py-4 border-b border-gray-200 flex justify-between items-center bg-gray-50">
            <h3 class="text-lg font-semibold text-gray-800">All Teachers</h3>
            <div class="relative">
                <input v-model="searchQuery" type="text" placeholder="Search teachers..." class="pl-10 pr-4 py-2 border border-gray-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent" />
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
                        <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
                        <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Username</th>
                        <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Subjects</th>
                        <th scope="col" class="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
                    </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                    <tr v-for="teacher in filteredTeachers" :key="teacher.id" class="hover:bg-gray-50 transition-colors">
                        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ teacher.id }}</td>
                         <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">{{ teacher.user.username }}</td>
                        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ teacher.subjects }}</td>
                        <td class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                            <button @click="deleteTeacher(teacher.id)" class="text-red-400 hover:text-red-600 transition-colors">
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

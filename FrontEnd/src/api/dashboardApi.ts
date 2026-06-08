import { apiClient } from './client';
import type { HrDashboardData } from '../types';

export const dashboardApi = {
  getHrStats: () =>
    apiClient.get<HrDashboardData>('/dashboard/hr').then((r) => r.data),
};

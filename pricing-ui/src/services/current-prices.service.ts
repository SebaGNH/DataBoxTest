import { axiosInstance } from './api.service';
import { Resource, Modality } from '../models/resource.model';

export const currentPricesService = {
    getArticlesCurrentPrices: async (page: number, pageSize: number = 10, filters?: any) => {
        const params = {
            idCsv: filters.csvId,
            idCarrera: filters.careerId,
            idCau: filters.cauId,
            idTipoModalidad: filters.modalityId,
            periodoAcademico: filters.periodId,
            tipoArancelId: filters.arancelId,
            idTipoTicket: filters.ticketId,
            tipoAlumno: filters.studentTypeId,
            fechaMin: filters.startDate,
            fechaMax: filters.endDate,
            pageNo: page.toString(),
            pageSize: pageSize.toString()
        };
        const map: any = new Map(Object.entries(params));
        for (const [key, value] of map) {
            if (!value) {
                map.delete(key);
            }
        }
        const obj = Object.fromEntries(map);
        const searchParams = new URLSearchParams(obj);
        const query = searchParams.toString();
        const { data } = await axiosInstance.get(`/articles/current-prices?${query}`);
        return data;
    },
    getCareers: async () => {
        const { data } = await axiosInstance.get('/resources/carreras');
        const dataMap = data.map((item: Resource) => ({ id: item.id, description: item.descripcion }));
        return dataMap;
    },
    getModalities: async () => {
        const { data } = await axiosInstance.get('/resources/modalidades');
        const dataMap = data.map((item: Modality) => ({ id: item.id, description: item.descripcion, modalidad: item.modalidad }));
        return dataMap;
    },
    getCaus: async () => {
        const { data } = await axiosInstance.get('/resources/cau');
        const dataMap = data.map((item: Resource) => ({ id: item.id, description: item.descripcion }));
        return dataMap;
    },
    getPeriods: async () => {
        const { data } = await axiosInstance.get('/resources/periodos');
        const dataMap = data.map((item: Resource) => ({ id: item.id, description: item.descripcion }));
        return dataMap;
    },
    getArancels: async () => {
        const { data } = await axiosInstance.get('/resources/aranceles');
        const dataMap = data.map((item: Resource) => ({ id: item.id, description: item.descripcion }));
        return dataMap;
    },
    getTickets: async () => {
        const { data } = await axiosInstance.get('/resources/ticket');
        const dataMap = data.map((item: Resource) => ({ id: item.id, description: item.descripcion }));
        return dataMap;
    },
    getTurns: async () => {
        const { data } = await axiosInstance.get('/resources/turnos');
        const dataMap = data.map((item: Resource) => ({ id: item.id, description: item.descripcion }));
        return dataMap;
    },
    getStatus: async () => {
        const { data } = await axiosInstance.get('/resources/status');
        const dataMap = data.map((item: Resource) => ({ id: item.id, description: item.descripcion }));
        return dataMap;
    }
};

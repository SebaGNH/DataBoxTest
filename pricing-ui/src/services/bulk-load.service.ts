import { axiosInstance } from './api.service';

export const bulkLoadService = {
    fileUpload: (file: any) => {
        const formData = new FormData();
        formData.append('file', file);
        return axiosInstance.post('/articles/file', formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
    },
    getRecentLots: async () => {
        const { data } = await axiosInstance.get('/lotes/lotesRecientes');
        return data;
    },
    getArticlesLoads: async (page: number, pageSize: number = 10, filters?: any) => {
        const params = {
            idCsv: filters.csvId,
            idCarrera: filters.careerId,
            idCau: filters.cauId,
            idTipoModalidad: filters.modalityId,
            periodoAcademico: filters.periodId,
            tipoArancelId: filters.arancelId,
            idTipoTicket: filters.ticketId,
            turnoCursado: filters.turnoId,
            tipoAlumno: filters.studentTypeId,
            idEstado: filters.statusId,
            fechaMax: filters.fechaMax,
            fechaMin: filters.fechaMin,
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
        const { data } = await axiosInstance.get(`/csv/current-records?${query}`);
        return data;
    },
    getFilterLotes: async (page: number, pageSize: number = 10, filters?: any) => {
        const params = {
            idLote: filters.idLote,
            nombreArchivo: filters.nombreArchivo,
            fechaMax: filters.fechaMax,
            fechaMin: filters.fechaMin,
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

        const { data } = await axiosInstance.get(`/lotes/filterLotes?${query}`);
        return data;
    },
    editLog: async function (editValues: any) {
        const { data } = await axiosInstance.put('/csv', editValues);
        return data;
    }
};

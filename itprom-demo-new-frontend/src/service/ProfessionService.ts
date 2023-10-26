import Profession from "@/model/Profession";
import axios from "axios";

export const professionService = {
    async create(profession: Profession): Promise<Profession> {
        return (await axios.post('/api/profession', profession)).data
    },
    async getAll(): Promise<Profession[]> {
        return (await axios.get('/api/profession')).data
    },
    async update(id: number, profession: Profession): Promise<Profession> {
        return (await axios.put(`/api/profession/${id}`, profession)).data
    },
    async delete(id: number): Promise<void | Error> {
        try {
            await axios.delete(`/api/profession/${id}`)
        } catch (error: any) {
            throw new Error(error?.response?.data?.detail)
        }
    }
}
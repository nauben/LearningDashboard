import {User} from './user'

export class Task {
    id: string;
    swimlane:number;
    title:string;
    description: string;
    dueDate: number[];
    assignees: User[];
    created: number[];
    updated: number[];
    label:number;
}
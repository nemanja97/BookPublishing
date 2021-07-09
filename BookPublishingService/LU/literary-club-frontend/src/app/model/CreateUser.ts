import { Genre } from "./enum/Genres";

export interface CreateUser {
  name: string;
  surname: string;
  username: string;
  email: string;
  password: string;
  city: string;
  country: string;
  genres: Genre[];
  betaReader: boolean | null;
}

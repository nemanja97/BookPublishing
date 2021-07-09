export class EditorBook {
  constructor(
    public id: string,
    public title: string,
    public author: string,
    public synopsis: string,
    public status: string,
    public taskId: string,
    public taskName: string
  ) {
  }
}

import { Directive, Input, TemplateRef, ViewContainerRef } from '@angular/core';
import { Role } from 'src/app/model/enum/Roles';
import { UserService } from 'src/app/services/user.service';

@Directive({
  selector: '[appShowAuthed]',
})
export class ShowAuthedDirective {
  constructor(
    private templateRef: TemplateRef<any>,
    private userService: UserService,
    private viewContainer: ViewContainerRef
  ) {}

  condition: Role;

  ngOnInit() {
    this.userService.currentUserRole.subscribe((role) => {
      if (role === this.condition) {
        this.viewContainer.createEmbeddedView(this.templateRef);
      } else {
        this.viewContainer.clear();
      }
    });
  }

  @Input() set appShowAuthed(condition: string) {
    switch (condition) {
      case 'HEAD_EDITOR':
        this.condition = Role.HEAD_EDITOR;
        break;
      case 'EDITOR':
        this.condition = Role.EDITOR;
        break;
      case 'WRITER':
        this.condition = Role.WRITER;
        break;
      case 'READER':
        this.condition = Role.READER;
        break;
      case 'LECTOR':
        this.condition = Role.LECTOR;
        break;
      case 'UNREGISTERED':
        this.condition = Role.UNREGISTERED;
        break;
    }
  }
}

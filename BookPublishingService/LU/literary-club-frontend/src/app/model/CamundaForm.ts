import CamundaFormItem from "./CamundaFormItem";

export default interface CamundaForm {
  taskId: string,
  formFields: CamundaFormItem[],
  processInstanceId: string
}

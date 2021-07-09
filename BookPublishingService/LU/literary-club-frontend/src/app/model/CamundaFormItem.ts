export default interface CamundaFormItem {
  businessKey: boolean,
  defaultValue: string | null,
  id: string,
  label: string,
  properties: {
    type: string,
    choices?: any,
    min_files?: any
  },
  type: {
    name: string
  },
  typeName: string,
  validationConstraints: [
    {
      name: string,
      configuration: string | null,
    }
  ],
  value: {
    transient: boolean,
    type: {
      name: string,
      javaType: string,
      primitiveValueType: boolean,
      abstract: boolean,
      parent: any,
    },
    value: string
  }
}

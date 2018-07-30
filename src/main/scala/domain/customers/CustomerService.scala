package domain.customers

class CustomerService(customerDao:CustomerDao) {
  def getList() = {
    customerDao.getAll()
  }
  def getBy(id:Int) = {
    customerDao.getById(id)
  }
  def add(customer: Customer) ={
    customerDao.create(customer)
  }
  def update(customer: Customer) = {
    customerDao.update(customer)
  }
  def removeBy(id:Int) = {
    customerDao.delete(id)
  }
}

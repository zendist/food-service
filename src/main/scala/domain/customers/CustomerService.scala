package domain.customers

object CustomerService {
  def getList() = {
    CustomerDao.getAll()
  }
  def getBy(id:Int) = {
    CustomerDao.getById(id)
  }
  def add(customer: Customer) ={
    CustomerDao.create(customer)
  }
  def update(customer: Customer) = {
    CustomerDao.update(customer)
  }
  def removeBy(id:Int) = {
    CustomerDao.delete(id)
  }
}

import java.util.Scanner;


public class Main {
    public static int calcBaseSalary(int baseDailySalary, int numDaysWorked) {
        return baseDailySalary * numDaysWorked;
    }

    public static int calcSalesSalary(int salesAmount) {
        double salesComissionPercent = 0.2;

        return (int) Math.round(salesAmount * salesComissionPercent);
    }

    public static int calcTransportationSalary(int baseSalary) {
        int monthlyMinSalary = 1300000;

        if (baseSalary <= monthlyMinSalary * 2) return 162000;
        return 0;
    }

    public static int calcExtraHoursSalary(int baseDailySalary, int numExtraHours) {
        double hourValue = (double) baseDailySalary / 8;
        double extraHourPercent = 0.3;

        double extraHourValue = hourValue + (hourValue * extraHourPercent);

        return (int) Math.round(extraHourValue * numExtraHours);
    }

    public static SalaryDetails calcSalary(Employee employee) {

        int baseSalary = calcBaseSalary(employee.baseDailySalary(), employee.numDaysWorked());
        int transportationSalary = calcTransportationSalary(baseSalary);
        int extraHoursSalary = calcExtraHoursSalary(employee.baseDailySalary(), employee.numExtraHours());
        int salesSalary = calcSalesSalary(employee.salesAmount());
        int netSalary = baseSalary + transportationSalary + extraHoursSalary + salesSalary - employee.loans();

        return new SalaryDetails(employee.fullName(), baseSalary, transportationSalary, extraHoursSalary, salesSalary, netSalary);
    }

    public static void printSalary(Employee employee, SalaryDetails salaryDetails) {
        System.out.println("Nombre: " + employee.fullName());
        System.out.println("Identificación: " + employee.identification());
        System.out.println("Salario base: " + salaryDetails.baseSalary());
        System.out.println("Auxilio de transporte: " + salaryDetails.transportationSalary());
        System.out.println("Comisión por ventas: " + salaryDetails.salesSalary());
        System.out.println("Horas extras: " + salaryDetails.extraHoursSalary());
        System.out.println("Préstamos: " + employee.loans());
        System.out.println("Salario neto: " + salaryDetails.netSalary());
    }

    public static void printHighestNetSalary(SalaryDetails salaryDetails) {
        System.out.println("El empleado con el salario neto más alto lo tiene: " + salaryDetails.fullName());
        System.out.println("Salario base: " + salaryDetails.baseSalary());
    }

    public static void printLowestNetSalary(SalaryDetails salaryDetails) {
        System.out.println("El empleado con el salario neto más bajo lo tiene: " + salaryDetails.fullName());
        System.out.println("Salario base: " + salaryDetails.baseSalary());
    }

    public static void printAverageBaseSalary(int averageBaseSalary) {
        System.out.println("El salario base promedio es: " + averageBaseSalary);
    }

    public static Employee initEmployee(int employeeNumber, Scanner Scanner) {
        System.out.println("Introduzca el nombre completo del empleado " + employeeNumber);
        String fullName = Scanner.nextLine();

        System.out.println("Introduzca la identificación de " + fullName);
        String identification = Scanner.nextLine();

        System.out.println("Introduzca el salario base diario de " + fullName);
        int basicSalary = Scanner.nextInt();

        System.out.println("Introduzca el numero de días trabajados de " + fullName);
        int numDaysWorked = Scanner.nextInt();

        System.out.println("Introduzca el numero de ventas de " + fullName);
        int numOfSales = Scanner.nextInt();

        System.out.println("Introduzca el numero de horas extras de " + fullName);
        int numExtraHours = Scanner.nextInt();

        System.out.println("Introduzca la cantidad a deducir por prestamos de " + fullName);
        int loans = Scanner.nextInt();

        return new Employee(fullName, identification, basicSalary, numDaysWorked, numOfSales, numExtraHours, loans);
    }

    public static void main(String[] args) {
        Scanner Scanner = new Scanner(System.in);

        System.out.println("Introduzca el número de empleados");
        int numEmployees = Scanner.nextInt();
        Scanner.nextLine();

        int totalBaseSalary = 0;

        Employee[] employees = new Employee[numEmployees];
        SalaryDetails[] salaryDetails = new SalaryDetails[numEmployees];

        SalaryDetails highestNetSalary = null;
        SalaryDetails lowestNetSalary = null;

        for (int i = 0; i < numEmployees; i++) {
            Employee employee = initEmployee(i + 1, Scanner);
            employees[i] = employee;

            SalaryDetails salaryDetail = calcSalary(employee);
            salaryDetails[i] = salaryDetail;
        }

        for (int i = 0; i < numEmployees; i++) {
            Employee employee = employees[i];
            SalaryDetails salaryDetail = salaryDetails[i];

            printSalary(employee, salaryDetail);

            totalBaseSalary += salaryDetail.baseSalary();

            highestNetSalary = highestNetSalary == null || salaryDetails.netSalary() > highestNetSalary.netSalary() ? salaryDetails : highestNetSalary;
            lowestNetSalary = lowestNetSalary == null || salaryDetails.netSalary() < lowestNetSalary.netSalary() ? salaryDetails : lowestNetSalary;
        }

        int averageBaseSalary = totalBaseSalary / numEmployees;

        if (highestNetSalary != null) printHighestNetSalary(highestNetSalary);
        if (lowestNetSalary != null) printLowestNetSalary(lowestNetSalary);
        printAverageBaseSalary(averageBaseSalary);
    }
}
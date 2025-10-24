package com.clinic;

import com.clinic.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class Main {

    private static EntityManagerFactory emf;

    public static void main(String[] args) {
        // Inicializa el EntityManagerFactory (unidad de persistencia definida en persistence.xml)
        emf = Persistence.createEntityManagerFactory("my-persistence-unit");

        System.out.println("=========================================");
        System.out.println("INICIANDO PRÁCTICA JPA AVANZADA MANUAL");
        System.out.println("=========================================");

        // Ejecutar Persistencia (Punto 2)
        loadInitialData();

        // Ejecutar Consultas (Puntos 3 al 10)
        executeQueries();

        // Cerrar recursos
        emf.close();
        System.out.println("\nJPA finalizado y recursos cerrados.");
    }

    /**
     * Punto 2: Persistencia de datos iniciales.
     */
    private static void loadInitialData() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        System.out.println("\n--- 2. Persistencia de Datos ---");

        try {
            // 1. Historiales Clínicos (2)
            HistoriaClinica hc1 = new HistoriaClinica("Historial de migrañas y alergia a penicilina.");
            HistoriaClinica hc2 = new HistoriaClinica("Paciente con diabetes tipo 2.");
            HistoriaClinica hc3 = new HistoriaClinica("Historial libre de enfermedades crónicas.");

            // 2. Medicamentos (3)
            Medicamento m1 = new Medicamento("Paracetamol", "Acetaminofén", 500);
            Medicamento m2 = new Medicamento("Metformina", "Biguanida", 850);
            Medicamento m3 = new Medicamento("Ibuprofeno", "Analgésico", 400);

            em.persist(m1);
            em.persist(m2);
            em.persist(m3);

            // 3. Pacientes (3)
            // p1: > 30, OSDE, con m1 y m3
            Paciente p1 = new Paciente("Ana", "García", 35, 12345678, "OSDE", LocalDate.of(1990, 5, 15), 'F');
            p1.setHistoriaClinica(hc1);
            p1.setMedicamentos(Set.of(m1, m3));

            // p2: < 30, PAMI, con m3
            Paciente p2 = new Paciente("Luis", "Martínez", 28, 87654321, "PAMI", LocalDate.of(1997, 10, 20), 'M');
            p2.setHistoriaClinica(hc3);
            p2.setMedicamentos(Set.of(m3));

            // p3: > 30, OSDE, con m2
            Paciente p3 = new Paciente("Sofía", "Rojas", 42, 11223344, "OSDE", LocalDate.of(1983, 3, 1), 'F');
            p3.setHistoriaClinica(hc2);
            p3.setMedicamentos(Set.of(m2));

            em.persist(p1);
            em.persist(p2);
            em.persist(p3);

            // 4. Médicos (2)
            Medico med1 = new Medico("Juan", "Pérez", 45, "Cardiología", "MP1234");
            Medico med2 = new Medico("María", "López", 55, "Pediatría", "MP5678");

            em.persist(med1);
            em.persist(med2);

            // 5. Consultas (4)
            Consulta c1 = new Consulta(LocalDate.now().minusDays(2), "Dolor de cabeza, tensión arterial normal.");
            c1.setPaciente(p1);
            c1.setMedico(med1);

            Consulta c2 = new Consulta(LocalDate.now().minusDays(5), "Control pediátrico de rutina.");
            c2.setPaciente(p2);
            c2.setMedico(med2);

            Consulta c3 = new Consulta(LocalDate.now().minusMonths(1), "Chequeo anual, se deriva a nutrición.");
            c3.setPaciente(p3);
            c3.setMedico(med1);

            Consulta c4 = new Consulta(LocalDate.now().minusDays(10), "Seguimiento por diabetes, ajuste de dosis.");
            c4.setPaciente(p3);
            c4.setMedico(med1);

            em.persist(c1);
            em.persist(c2);
            em.persist(c3);
            em.persist(c4);

            em.getTransaction().commit();
            System.out.println("Persistencia completada: 3 Pacientes, 2 Médicos, 4 Consultas, 3 Medicamentos, 3 Historias Clínicas.");

            // Guardamos los IDs para las consultas
            p1.setId(p1.getId());
            med1.setId(med1.getId());

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error durante la persistencia: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Puntos 3 al 10: Ejecución de consultas JPQL.
     */
    private static void executeQueries() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try {
            System.out.println("\n=========================================");
            System.out.println("EJECUTANDO CONSULTAS JPQL (PUNTOS 3 - 10)");
            System.out.println("=========================================");

            // --- 3. Listar todos los pacientes mayores de 30 años.
            System.out.println("\n--- 3. Pacientes Mayores de 30 años:");
            TypedQuery<Paciente> q3 = em.createQuery("SELECT p FROM Paciente p WHERE p.edad > 30 AND p.eliminado = false", Paciente.class);
            q3.getResultList().forEach(p -> System.out.println("  ID: " + p.getId() + ", Nombre: " + p.getNombre() + ", Edad: " + p.getEdad()));

            // --- 4. Obtener todas las consultas realizadas por un médico específico. (Usaremos a Juan Pérez, ID 1 si el autoincremento empieza en 1)
            System.out.println("\n--- 4. Consultas atendidas por el Médico con ID=1:");
            Long medicoId = 1L;
            TypedQuery<Consulta> q4 = em.createQuery("SELECT c FROM Consulta c WHERE c.medico.id = :medicoId AND c.eliminado = false", Consulta.class)
                    .setParameter("medicoId", medicoId);
            q4.getResultList().forEach(c -> System.out.println("  Fecha: " + c.getFecha() + ", Diagnóstico: " + c.getDiagnostico()));

            // --- 5. Mostrar todos los medicamentos asociados a un paciente. (Usaremos a Ana García, ID 1)
            System.out.println("\n--- 5. Medicamentos asociados al Paciente con ID=1:");
            Long pacienteId = 1L;
            // Usamos un JOIN FETCH para asegurar que la colección se cargue
            TypedQuery<Medicamento> q5 = em.createQuery("SELECT m FROM Paciente p JOIN p.medicamentos m WHERE p.id = :pacienteId AND p.eliminado = false", Medicamento.class)
                    .setParameter("pacienteId", pacienteId);
            q5.getResultList().forEach(m -> System.out.println("  - " + m.getNombre() + " (" + m.getDroga() + ")"));

            // --- 6. Listar las consultas con su diagnóstico y el nombre del paciente.
            System.out.println("\n--- 6. Diagnóstico y Nombre del Paciente por Consulta:");
            Query q6 = em.createQuery("SELECT c.diagnostico, p.nombre, p.apellido FROM Consulta c JOIN c.paciente p WHERE c.eliminado = false");
            List<Object[]> resultados6 = q6.getResultList();
            resultados6.forEach(obj -> System.out.println("  Diagnóstico: " + obj[0] + " | Paciente: " + obj[1] + " " + obj[2]));

            // --- 7. Calcular el promedio de edad de los pacientes.
            System.out.println("\n--- 7. Promedio de Edad de los Pacientes:");
            Query q7 = em.createQuery("SELECT AVG(p.edad) FROM Paciente p WHERE p.eliminado = false");
            Double promedioEdad = (Double) q7.getSingleResult();
            System.out.printf("  Promedio: %.2f años\n", promedioEdad);

            // --- 8. Listar todos los pacientes que tienen una obra social específica.
            System.out.println("\n--- 8. Pacientes con Obra Social 'OSDE':");
            TypedQuery<Paciente> q8 = em.createQuery("SELECT p FROM Paciente p WHERE p.obraSocial = :osName AND p.eliminado = false", Paciente.class)
                    .setParameter("osName", "OSDE");
            q8.getResultList().forEach(p -> System.out.println("  - " + p.getNombre() + " " + p.getApellido()));

            // --- 9. Mostrar los médicos y la cantidad de consultas que atendieron.
            System.out.println("\n--- 9. Médicos y Cantidad de Consultas:");
            Query q9 = em.createQuery("SELECT m.nombre, m.apellido, COUNT(c) FROM Medico m JOIN m.consultas c WHERE m.eliminado = false GROUP BY m.id, m.nombre, m.apellido");
            List<Object[]> resultados9 = q9.getResultList();
            resultados9.forEach(obj -> System.out.println("  Médico: " + obj[0] + " " + obj[1] + " | Cantidad: " + obj[2]));

            // --- 10. Obtener todos los pacientes junto con la descripción de su historia clínica.
            System.out.println("\n--- 10. Pacientes y Descripción de Historia Clínica:");
            Query q10 = em.createQuery("SELECT p.nombre, p.apellido, h.descripcion FROM Paciente p JOIN p.historiaClinica h WHERE p.eliminado = false");
            List<Object[]> resultados10 = q10.getResultList();
            resultados10.forEach(obj -> System.out.println("  Paciente: " + obj[0] + " " + obj[1] + " | HC: " + obj[2]));


            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error durante la ejecución de consultas: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
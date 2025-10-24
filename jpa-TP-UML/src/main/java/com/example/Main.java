package com.example;

import com.example.Entity.*;
import jakarta.persistence.*;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidad");
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            System.out.println("\nCreando y Persistiendo entidades (datos argentinos)...");

            // Crear medicamentos (nombres comerciales o genéricos comunes en ARG)
            Medicamento med1 = Medicamento.builder().nombre("Sertal Compuesto").droga("Propinox + Clonixinato de Lisina").pesoEnGramos(30).build();
            Medicamento med2 = Medicamento.builder().nombre("Lotrial").droga("Enalapril (antihipertensivo)").pesoEnGramos(10).build();
            Medicamento med3 = Medicamento.builder().nombre("T4 Montpellier").droga("Levotiroxina Sódica").pesoEnGramos(100).build();
            Medicamento med4 = Medicamento.builder().nombre("Amoxidal 500").droga("Amoxicilina").pesoEnGramos(500).build();

            // Persistir medicamentos primero
            em.persist(med1); em.persist(med2); em.persist(med3); em.persist(med4);

            // Crear médicos (Nombres y matrículas argentinas)
            Medico m1 = Medico.builder().nombre("Horacio").apellido("Giménez").especialidad("Clínica Médica").matricula("MN 89341").edad(55).build();
            Medico m2 = Medico.builder().nombre("Florencia").apellido("Pérez").especialidad("Ginecología").matricula("MP 21567").edad(42).build();

            // Crear pacientes con sus historias clínicas
            HistoriaClinica hc1 = HistoriaClinica.builder().descripcion("Paciente con hipotiroidismo diagnosticado hace 5 años.").build();
            HistoriaClinica hc2 = HistoriaClinica.builder().descripcion("Antecedentes de gastritis crónica por H. Pylori.").build();

            Paciente p1 = Paciente.builder().nombre("Marcelo").apellido("Toledo").dni(23876543).edad(52).obraSocial("PAMI").fechaNacimiento(LocalDate.of(1973, 8, 12)).sexo('M').historiaClinica(hc1).build();
            Paciente p2 = Paciente.builder().nombre("Gabriela").apellido("Rojas").dni(35123987).edad(38).obraSocial("OSDE").fechaNacimiento(LocalDate.of(1987, 5, 25)).sexo('F').historiaClinica(hc2).build();
            Paciente p3 = Paciente.builder().nombre("Ricardo").apellido("Iorio").dni(14998776).edad(61).obraSocial(null).fechaNacimiento(LocalDate.of(1964, 2, 22)).sexo('M').build();

            // Establecer relación bidireccional para historias clínicas
            hc1.setPaciente(p1); hc2.setPaciente(p2);

            // Establecer relaciones ManyToMany Paciente-Medicamento
            p1.addMedicamento(med2); p1.addMedicamento(med3); p2.addMedicamento(med1);

            // Crear consultas y establecer relaciones
            Consulta c1 = Consulta.builder().fecha(LocalDate.of(2023, 10, 10)).diagnostico("Control de Hipertensión Arterial").medico(m1).paciente(p1).build();
            Consulta c2 = Consulta.builder().fecha(LocalDate.of(2023, 11, 28)).diagnostico("Dolor abdominal cólico (presunto cólico renal)").medico(m1).paciente(p2).build();
            Consulta c3 = Consulta.builder().fecha(LocalDate.of(2023, 12, 5)).diagnostico("Control ginecológico de rutina (PAP)").medico(m2).paciente(p2).build();
            Consulta c4 = Consulta.builder().fecha(LocalDate.of(2024, 1, 12)).diagnostico("Certificado de aptitud física para natación").medico(m1).paciente(p3).build();

            // Usar los métodos helper para establecer relaciones bidireccionales
            p1.addConsulta(c1); p2.addConsulta(c2); p2.addConsulta(c3); p3.addConsulta(c4);

            // Persistir médicos y pacientes
            em.persist(m1); em.persist(m2); em.persist(p1); em.persist(p2); em.persist(p3);

            System.out.println("Datos iniciales persistidos correctamente.\n");

            // ----------------------------------------------------------------------
            //                  INICIO: MODIFICACIÓN DE DATOS
            // ----------------------------------------------------------------------
            System.out.println("---------------------------------------------------------");
            System.out.println("               MODIFICACIÓN DE DATOS (UPDATE)            ");
            System.out.println("---------------------------------------------------------");

            // 1. Modificar un Médico (Horacio Giménez -> Claudio, Cardiología)
            TypedQuery<Medico> queryM1 = em.createQuery("SELECT m FROM Medico m WHERE m.matricula = :matricula", Medico.class);
            queryM1.setParameter("matricula", "MN 89341");
            Medico medicoAModificar = queryM1.getSingleResult();

            if (medicoAModificar != null) {
                System.out.println("\n[Medico MN 89341] Antes: " + medicoAModificar.getNombre() + ", " + medicoAModificar.getEspecialidad());
                medicoAModificar.setNombre("Claudio"); // Cambiar nombre
                medicoAModificar.setEspecialidad("Cardiología"); // Cambiar especialidad
                System.out.println("[Medico MN 89341] Después: " + medicoAModificar.getNombre() + ", " + medicoAModificar.getEspecialidad());
            }

            // 2. Modificar un Paciente DNI (Marcelo Toledo)
            TypedQuery<Paciente> queryP1 = em.createQuery("SELECT p FROM Paciente p WHERE p.nombre = :nombre AND p.apellido = :apellido", Paciente.class);
            queryP1.setParameter("nombre", "Marcelo");
            queryP1.setParameter("apellido", "Toledo");
            Paciente pacienteAModificar = queryP1.getSingleResult();

            if (pacienteAModificar != null) {
                System.out.println("\n[Paciente Marcelo Toledo] DNI Antes: " + pacienteAModificar.getDni());
                int nuevoDni = 23876544;
                pacienteAModificar.setDni(nuevoDni); // Cambiar DNI
                System.out.println("[Paciente Marcelo Toledo] DNI Después: " + pacienteAModificar.getDni());
            }

            // 3. Añadir un Medicamento a un Paciente (Gabriela Rojas)
            TypedQuery<Paciente> queryP2 = em.createQuery("SELECT p FROM Paciente p WHERE p.nombre = :nombre AND p.apellido = :apellido", Paciente.class);
            queryP2.setParameter("nombre", "Gabriela");
            queryP2.setParameter("apellido", "Rojas");
            Paciente pacienteGabriela = queryP2.getSingleResult();

            if (pacienteGabriela != null) {
                pacienteGabriela.addMedicamento(med4); // Añadir Amoxidal 500
                System.out.println("\n[Paciente Gabriela Rojas] Se añadió el medicamento: " + med4.getNombre());
            }

            System.out.println("---------------------------------------------------------");
            System.out.println("             FIN: MODIFICACIÓN DE DATOS                  ");
            System.out.println("---------------------------------------------------------");

            // ----------------------------------------------------------------------
            //                  INICIO: CONSULTAS Y LISTADOS ORDENADOS
            // ----------------------------------------------------------------------

            // 1. Listar pacientes Mayores a 50 años
            System.out.println("\n\n-- 1. PACIENTES MAYORES A 50 AÑOS 🎂 --");
            TypedQuery<Paciente> query = em.createQuery("SELECT p FROM Paciente p WHERE (edad > 50)", Paciente.class);
            System.out.printf("| %-15s | %-15s | %-5s | %-12s |\n", "Nombre", "Apellido", "Edad", "Obra Social");
            System.out.println("|-----------------|-----------------|-------|--------------|");
            query.getResultList().forEach(p -> System.out.printf("| %-15s | %-15s | %-5d | %-12s |\n",
                    p.getNombre(), p.getApellido(), p.getEdad(),
                    p.getObraSocial() != null ? p.getObraSocial() : "N/A"));

            // 2. Consultas realizadas por Dra. Florencia Pérez
            System.out.println("\n\n-- 2. CONSULTAS DE DRA. FLORENCIA PÉREZ 👩‍⚕️ --");
            TypedQuery<Object[]> queryMedicoEspecifico = em.createQuery(
                    "SELECT c.fecha, c.diagnostico, p.nombre, p.apellido " +
                            "FROM Consulta c JOIN c.medico m JOIN c.paciente p " +
                            "WHERE m.nombre = 'Florencia' AND m.apellido = 'Pérez' " +
                            "ORDER BY c.fecha", Object[].class);

            System.out.printf("| %-10s | %-40s | %-20s |\n", "Fecha", "Diagnóstico", "Paciente");
            System.out.println("|------------|------------------------------------------|----------------------|");
            queryMedicoEspecifico.getResultList().forEach(result -> {
                LocalDate fecha = (LocalDate) result[0];
                String diagnostico = (String) result[1];
                String nombrePaciente = (String) result[2];
                String apellidoPaciente = (String) result[3];
                System.out.printf("| %-10s | %-40s | %-20s |\n",
                        fecha, diagnostico, nombrePaciente + " " + apellidoPaciente);
            });


            // 3. Medicamentos asociados al paciente Marcelo Toledo (DNI modificado)
            System.out.println("\n\n-- 3. MEDICAMENTOS DE MARCELO TOLEDO (DNI: 23876544) 💊 --");
            TypedQuery<Medicamento> queryMed = em.createQuery("SELECT m FROM Paciente p JOIN p.medicamentos m WHERE p.dni = :dni", Medicamento.class);
            queryMed.setParameter("dni", 23876544);
            System.out.printf("| %-20s | %-30s |\n", "Nombre Comercial", "Droga");
            System.out.println("|----------------------|--------------------------------|");
            queryMed.getResultList().forEach(m -> System.out.printf("| %-20s | %-30s |\n", m.getNombre(), m.getDroga()));

            // 4. Medicamentos asociados al paciente Gabriela Rojas (Amoxidal añadido)
            System.out.println("\n\n-- 4. MEDICAMENTOS DE GABRIELA ROJAS (Amoxidal añadido) 💊 --");
            TypedQuery<Medicamento> queryMedGabriela = em.createQuery("SELECT m FROM Paciente p JOIN p.medicamentos m WHERE p.nombre = 'Gabriela' AND p.apellido = 'Rojas'", Medicamento.class);
            System.out.printf("| %-20s | %-30s |\n", "Nombre Comercial", "Droga");
            System.out.println("|----------------------|--------------------------------|");
            queryMedGabriela.getResultList().forEach(m -> System.out.printf("| %-20s | %-30s |\n", m.getNombre(), m.getDroga()));


            // 5. Listar las consultas con su diagnóstico y el nombre del paciente
            System.out.println("\n\n-- 5. LISTADO COMPLETO DE CONSULTAS 📝 --");
            TypedQuery<Object[]> queryCons = em.createQuery("SELECT c.fecha, c.diagnostico, p.nombre, p.apellido FROM Consulta c JOIN c.paciente p ORDER BY c.fecha", Object[].class);
            System.out.printf("| %-10s | %-40s | %-20s |\n", "Fecha", "Diagnóstico", "Paciente");
            System.out.println("|------------|------------------------------------------|----------------------|");
            queryCons.getResultList().forEach(result -> {
                LocalDate fecha = (LocalDate) result[0];
                String diagnostico = (String) result[1];
                String nombrePaciente = (String) result[2];
                String apellidoPaciente = (String) result[3];
                System.out.printf("| %-10s | %-40s | %-20s |\n",
                        fecha, diagnostico, nombrePaciente + " " + apellidoPaciente);
            });

            // 6. Calcular el promedio de edad de los pacientes
            TypedQuery<Double> queryAvg = em.createQuery("SELECT ROUND(AVG(p.edad), 2) FROM Paciente p", Double.class);
            Double promedioEdad = queryAvg.getSingleResult();
            System.out.println("\n\n-- 6. PROMEDIO DE EDAD DE PACIENTES 📊 --");
            System.out.println("Promedio de edad: " + promedioEdad + " años.");


            // 7. Listar todos los pacientes que tienen una obra social específica
            System.out.println("\n\n-- 7. PACIENTES CON OBRA SOCIAL OSDE 🩺 --");
            TypedQuery<Paciente> queryObraSocial = em.createQuery("SELECT p FROM Paciente p WHERE p.obraSocial = :obraSocial", Paciente.class);
            queryObraSocial.setParameter("obraSocial", "OSDE");
            System.out.printf("| %-15s | %-15s | %-5s | %-12s |\n", "Nombre", "Apellido", "Edad", "Obra Social");
            System.out.println("|-----------------|-----------------|-------|--------------|");
            queryObraSocial.getResultList().forEach(p -> System.out.printf("| %-15s | %-15s | %-5d | %-12s |\n",
                    p.getNombre(), p.getApellido(), p.getEdad(), p.getObraSocial()));

            // 8. Mostrar los médicos y la cantidad de consultas que atendieron
            System.out.println("\n\n-- 8. MÉDICOS Y CANTIDAD DE CONSULTAS ATENDIDAS 👨‍🔬 --");
            // Notar que el médico 'Horacio Giménez' ahora aparece como 'Claudio Giménez'
            TypedQuery<Object[]> queryMedicos = em.createQuery("SELECT m.nombre, m.apellido, COUNT(c) FROM Medico m LEFT JOIN m.consultas c GROUP BY m.id", Object[].class);
            System.out.printf("| %-15s | %-15s | %-10s |\n", "Nombre", "Apellido", "Consultas");
            System.out.println("|-----------------|-----------------|------------|");
            queryMedicos.getResultList().forEach(result -> {
                String nombreMedico = (String) result[0];
                String apellidoMedico = (String) result[1];
                Long cantidadConsultas = (Long) result[2];
                System.out.printf("| %-15s | %-15s | %-10d |\n", nombreMedico, apellidoMedico, cantidadConsultas);
            });


            // 9. Pacientes con descripción de Historia Clínica
            System.out.println("\n\n-- 9. HISTORIAS CLÍNICAS (Descripción) 📖 --");
            TypedQuery<Object[]> queryMulti = em.createQuery("SELECT p.nombre, p.apellido, h.descripcion FROM Paciente p JOIN p.historiaClinica h", Object[].class);
            System.out.printf("| %-15s | %-15s | %-60s |\n", "Nombre", "Apellido", "Descripción Historial");
            System.out.println("|-----------------|-----------------|--------------------------------------------------------------|");
            queryMulti.getResultList().forEach(result -> {
                String nombrePaciente = (String) result[0];
                String apellidoPaciente = (String) result[1];
                String descripcionHistorial = (String) result[2];
                System.out.printf("| %-15s | %-15s | %-60s |\n", nombrePaciente, apellidoPaciente, descripcionHistorial);
            });

            // El commit guarda todos los datos iniciales y las modificaciones
            tx.commit();

            System.out.println("\n=========================================================");
            System.out.println("   TRABAJO PRÁCTICO JPA - CLÍNICA MÉDICA (ARG) - FIN     ");
            System.out.println("=========================================================\n");

        } catch (Exception e) {
            System.err.println("Error de persistencia: " + e.getMessage());
            e.printStackTrace();
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
        }
    }
}
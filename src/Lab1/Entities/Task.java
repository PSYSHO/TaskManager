package Lab1.Entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class Task implements Serializable, Comparable<Task> {
    String name;
    String description;
    Date data;
    boolean relevant;
    LinkedList<Contacts> contactsList = new LinkedList<Contacts>();

    public Task(String name, String description, Date data, LinkedList<Contacts> contactsList) {
        this.name = name;
        this.description = description;
        this.data = data;
        this.contactsList = contactsList;
        relevant = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Contacts[] getContacts() {
        Contacts[] array = new Contacts[contactsList.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = contactsList.get(i);
        }
        return array;
    }

    public String[] getContactsString() {
            String[] contacts=new String[contactsList.size()];
            for (int i = 0; i < contacts.length; i++) {
                contacts[i]=getContacts()[i].getName()+" "+getContacts()[i].getPhone()+"\n";
            }
            return contacts;
    }

    public void setContacts(LinkedList<Contacts> contactsList) {
        this.contactsList = contactsList;
    }

    public boolean getRelevant() {
        return relevant;
    }

    public void setRelevant(boolean relevant) {
        this.relevant = relevant;
    }

    public String toString() {
        StringBuffer str = new StringBuffer();
        int i = 0;
        SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm dd:MM:yyyy ");
        str.append("Имя задачи:\t")
                .append(name + "\n")
                .append("Описание:\t")
                .append(description + "\n")
                .append("Дата:\t")
                .append(formatDate.format(data) + "\nКонтакты:\n");
        if (contactsList != null) {
            for (Contacts element : contactsList) {
                str.append(i + ". ")
                        .append(element.toString() + "\n");
                i++;
            }
        }
        return str.toString();
    }


    @Override
    public int compareTo(Task o) {
        return this.data.compareTo(o.data);
    }
    //вывод на экран контактов, изменение контактов по имени

}

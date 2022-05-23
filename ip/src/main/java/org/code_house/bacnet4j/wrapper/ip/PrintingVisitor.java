package org.code_house.bacnet4j.wrapper.ip;

import com.serotonin.bacnet4j.type.Encodable;
import com.serotonin.bacnet4j.type.primitive.OctetString;
import org.code_house.bacnet4j.wrapper.api.Device;
import org.code_house.bacnet4j.wrapper.api.Property;

import java.io.PrintStream;

class PrintingVisitor implements Visitor {

    private final PrintStream output;

    PrintingVisitor(PrintStream output) {
        this.output = output;
    }

    @Override
    public Flag visit(Device device) {
        output.println("  => Device id " + device.getInstanceNumber());
        output.println("     Metadata");
        if (device instanceof IpDevice) {
            IpDevice ipDevice = (IpDevice) device;
            output.println("       Address: " + ipDevice.getHostAddress() + ":" + ipDevice.getPort());
        } else {
            output.println("       Address: " + new OctetString(device.getAddress()).toString());
        }
        output.println("       Name: " + device.getName());
        output.println("       Model: " + device.getModelName());
        output.println("       Vendor: " + device.getVendorName());
        return Flag.CONTNUE;
    }

    @Override
    public Flag visit(Property property) {
        output.printf("          => Type %s id: %d%n",
            property.getType().name(),
            property.getId()
        );
        output.println("             Metadata");
        output.println("               Name: " + property.getName());
        output.println("               Units: " + property.getUnits());
        output.println("               Description: " + property.getDescription());

        return Flag.CONTNUE;
    }

    @Override
    public Flag visit(Encodable propertyValue) {
        output.printf("             Present value %s, type: %s%n",
            propertyValue,
            propertyValue != null ? propertyValue.getClass().getName() : "<null>"
        );

        return Flag.SKIP;
    }


    @Override
    public Flag visitAttribute(String attribute, Encodable propertyValue) {
        output.printf("             Attribute '%s' value %s, type: %s%n",
            attribute,
            propertyValue,
            propertyValue != null ? propertyValue.getClass().getName() : "<null>"
        );

        return Flag.SKIP;
    }
}
